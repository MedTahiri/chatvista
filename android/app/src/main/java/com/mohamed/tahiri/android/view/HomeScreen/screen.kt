package com.mohamed.tahiri.android.view.HomeScreen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.R
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.Conversation
import com.mohamed.tahiri.android.model.ConversationTitle
import com.mohamed.tahiri.android.model.ImageMapper
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.model.newConversation
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.view.InternetProblem
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.ConversationViewModel
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    dataStoreViewModel: DataStoreViewModel,
    conversationViewModel: ConversationViewModel,
    context: Context
) {
    val usersState = userViewModel.users.value
    val userState = userViewModel.user.value
    val conversationsState = conversationViewModel.conversationByUser.value
    val conversationState = conversationViewModel.conversation.value
    val shouldShowDialog = remember { mutableStateOf(false) }
    val contactSelected = remember {
        mutableStateOf(
            User(
                id = -1,
                fullName = "",
                email = "",
                password = "",
                image = ""
            )
        )
    }
    val userId by dataStoreViewModel.userId.collectAsState(-1)
    val currentUser = remember {
        mutableStateOf(
            User(
                id = userId,
                fullName = "",
                email = "",
                password = "",
                image = "0"
            )
        )
    }

    val imageMapper = ImageMapper()
    LaunchedEffect(userId) {
        while (true) {
            delay(2500)
            userViewModel.fetchUsers()
            conversationViewModel.getConversationByUser(userId)
            userViewModel.getUserById(userId)
        }
    }

    val switch = remember {
        mutableStateOf("Descussions")
    }
    val searchInContact = remember {
        mutableStateOf("")
    }
    val searchInMessage = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        Text(
                            text = switch.value,
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
//                        Text(
//                            text = "Hello, ${currentUser.value.fullName}",
//                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
//                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
//                        )
                    }

                    IconButton(
                        onClick = { navController.navigate(Screen.ProfileScreen.name) },
                        modifier = Modifier.padding(16.dp,0.dp)
                    ) {
                        val imageResource =
                            imageMapper.getImage(currentUser.value.image) ?: R.drawable.a
                        Image(
                            painter = painterResource(id = imageResource),
                            contentDescription = "User Image",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(24.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )

        if (switch.value == "Descussions") {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {

                OutlinedTextField(
                    value = searchInMessage.value,
                    onValueChange = { searchInMessage.value = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search for messages",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                when (conversationsState) {
                    is ApiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    is ApiState.Success<*> -> {
                        val conversations =
                            (conversationsState as ApiState.Success<List<ConversationTitle>>).data

                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(conversations) { conversation ->
                                Card(
                                    modifier = Modifier
                                        .padding(0.dp, 8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            navController.navigate(
                                                Screen.MessagingScreen.name + "/${conversation.id}/${conversation.fullName}/${conversation.image}"
                                            )
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val imageResource =
                                            imageMapper.getImage(conversation.image) ?: R.drawable.a
                                        Image(
                                            painter = painterResource(id = imageResource),
                                            contentDescription = "User Image",
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(RoundedCornerShape(28.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(
                                            modifier = Modifier.weight(1f)
                                        ) {
                                            Row(
                                                modifier = Modifier.fillMaxWidth(),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Text(
                                                    text = conversation.fullName,
                                                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                                    fontWeight = FontWeight.Bold,
                                                    color = MaterialTheme.colorScheme.onSurface
                                                )
                                                Text(
                                                    text = conversation.time,
                                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                                    color = MaterialTheme.colorScheme.onSurface.copy(
                                                        alpha = 0.6f
                                                    )
                                                )
                                            }
                                            Spacer(modifier = Modifier.height(4.dp))
                                            Text(
                                                text = conversation.lastMessage,
                                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.8f
                                                ),
                                                maxLines = 1
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    is ApiState.Error -> {
                        val error = (conversationsState as ApiState.Error).message
                        InternetProblem(onRetry = {
                            navController.navigate(Screen.HomeScreen.name)
                        }, error = error)
                    }
                }
            }

        } else {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
            ) {
                OutlinedTextField(
                    value = searchInContact.value,
                    onValueChange = { searchInContact.value = it },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "Search for contacts",
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                when (usersState) {
                    is ApiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    is ApiState.Success<*> -> {
                        val users = (usersState as ApiState.Success<List<User>>).data
                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                            items(users) { user ->
                                Card(
                                    modifier = Modifier
                                        .padding(0.dp, 8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            contactSelected.value = user
                                            shouldShowDialog.value = true
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.Start,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val imageResource =
                                            imageMapper.getImage(user.image) ?: R.drawable.a
                                        Image(
                                            painter = painterResource(id = imageResource),
                                            contentDescription = "User Image",
                                            modifier = Modifier
                                                .size(56.dp)
                                                .clip(RoundedCornerShape(28.dp)),
                                            contentScale = ContentScale.Crop
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Text(
                                            text = user.fullName,
                                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }
                        }
                    }


                    is ApiState.Error -> {
                        val error = (usersState as ApiState.Error).message
                        InternetProblem(onRetry = {
                            navController.navigate(Screen.HomeScreen.name)
                        }, error = error)
                    }
                }
            }
        }

        BottomAppBar(
            modifier = Modifier.fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { switch.value = "Descussions" }
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.discussions),
                        contentDescription = "Discussions",
                        tint = if (switch.value == "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.6f
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Discussions",
                        color = if (switch.value == "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.6f
                        )
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .clickable { switch.value = "Contacts" }
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.contacts),
                        contentDescription = "Contacts",
                        tint = if (switch.value == "Contacts") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.6f
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Contacts",
                        color = if (switch.value == "Contacts") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimaryContainer.copy(
                            alpha = 0.6f
                        )
                    )
                }
            }
        }
    }

    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = {
                Text(
                    text = "New Conversation",
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = "Do you want to create a new conversation with ${contactSelected.value.fullName}?",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        conversationViewModel.newConversation(
                            newConversation(
                                userId,
                                contactSelected.value.id
                            )
                        )
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Dismiss",
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            textContentColor = MaterialTheme.colorScheme.onSurface
        )
    }

    LaunchedEffect(conversationState) {
        when (conversationState) {
            is ApiState.Success<*> -> {
                val conversation = (conversationState as ApiState.Success<Conversation>).data

            }

            is ApiState.Error -> {
                val error = (conversationState as ApiState.Error).message

            }

            else -> {

            }
        }

    }
    LaunchedEffect(userState) {
        when (userState) {
            is ApiState.Loading -> {
            }

            is ApiState.Success<*> -> {
                val user = (userState as ApiState.Success<User>).data
                currentUser.value = user
            }

            is ApiState.Error -> {
            }
        }
    }
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {
    }
}