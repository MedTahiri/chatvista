package com.mohamed.tahiri.android.view.HomeScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.Conversation
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.model.newConversation
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.ConversationViewModel
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    dataStoreViewModel: DataStoreViewModel,
    conversationViewModel: ConversationViewModel,
    context: Context
) {
    val usersState = userViewModel.users.value
    val conversationsState = conversationViewModel.conversationByUser.value
    val conversationState = conversationViewModel.conversation.value
    //val userState = userViewModel.user.value
    val shouldShowDialog = remember { mutableStateOf(false) }
    //val isConversationCreated = remember { mutableStateOf(false) }
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

    userViewModel.fetchUsers()
    conversationViewModel.getConversationByUser(userId)
    //userViewModel.getUserById(userId)

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
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            if (switch.value == "Descussions") {
                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Descussions",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize.times(1.5),
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { navController.navigate(Screen.ProfileScreen.name) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchInMessage.value,
                            onValueChange = { searchInMessage.value = it },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                            }, placeholder = {
                                Text(text = "Search for messages")
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(contentAlignment = Alignment.TopCenter) {
                            when (conversationsState) {
                                is ApiState.Loading -> {
                                    // Show loading indicator
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }

                                is ApiState.Success<*> -> {
                                    val conversations = (conversationsState as ApiState.Success<List<Conversation>>).data
                                    Column {
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(items = conversations, itemContent = { conversation ->
                                                Card(modifier = Modifier
                                                    .padding(8.dp)
                                                    .clickable {

                                                    }, onClick = {
                                                    navController.navigate(Screen.MessagingScreen.name)
                                                }) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp),
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.AccountCircle,
                                                            contentDescription = "",
                                                            modifier = Modifier.size(65.dp)
                                                        )
                                                        Column(
                                                            modifier = Modifier,
                                                            horizontalAlignment = Alignment.Start,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            Text(text = conversation.id.toString())
                                                            Text(text = conversation.toString())
                                                        }
                                                    }
                                                }
                                            })
                                        }
                                    }
                                }

                                is ApiState.Error -> {
                                    val error = (conversationsState as ApiState.Error).message
                                    Text(
                                        text = "Failed to fetch data: $error",
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }
                    }
                }

            } else {

                Column(modifier = Modifier.padding(8.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Contacts",
                            fontSize = MaterialTheme.typography.titleLarge.fontSize.times(1.5),
                            fontWeight = FontWeight.Bold
                        )
                        IconButton(
                            onClick = { navController.navigate(Screen.ProfileScreen.name) },
                            modifier = Modifier.size(48.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "",
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        OutlinedTextField(
                            value = searchInContact.value,
                            onValueChange = { searchInContact.value = it },
                            leadingIcon = {
                                Icon(imageVector = Icons.Default.Search, contentDescription = "")
                            }, placeholder = {
                                Text(text = "Search for contacts")
                            },
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Box(contentAlignment = Alignment.TopCenter) {
                            when (usersState) {
                                is ApiState.Loading -> {
                                    // Show loading indicator
                                    CircularProgressIndicator(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                    )
                                }

                                is ApiState.Success<*> -> {
                                    val users = (usersState as ApiState.Success<List<User>>).data
                                    Column {
                                        LazyColumn(modifier = Modifier.fillMaxSize()) {
                                            items(items = users, itemContent = { user ->
                                                Card(modifier = Modifier
                                                    .padding(8.dp)
                                                    .clickable {
                                                        navController.navigate(Screen.MessagingScreen.name)
                                                    }, onClick = {
                                                    contactSelected.value = user
                                                    shouldShowDialog.value = true
                                                }) {
                                                    Row(
                                                        modifier = Modifier
                                                            .fillMaxWidth()
                                                            .padding(8.dp),
                                                        horizontalArrangement = Arrangement.Start,
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.AccountCircle,
                                                            contentDescription = "",
                                                            modifier = Modifier.size(65.dp)
                                                        )
                                                        Column(
                                                            modifier = Modifier,
                                                            horizontalAlignment = Alignment.Start,
                                                            verticalArrangement = Arrangement.Center
                                                        ) {
                                                            Text(text = user.fullName)
                                                        }
                                                    }
                                                }
                                            })
                                        }
                                    }
                                }

                                is ApiState.Error -> {
                                    val error = (usersState as ApiState.Error).message
                                    Text(
                                        text = "Failed to fetch data: $error",
                                        modifier = Modifier
                                            .padding(16.dp)
                                            .fillMaxWidth()
                                            .align(Alignment.Center)
                                    )
                                }
                            }
                        }

                    }

                }
            }

            BottomAppBar {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable { switch.value = "Descussions" }
                            .fillMaxSize()
                            .weight(1f, false)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle, contentDescription = "",
                            tint = if (switch.value == "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                        Text(
                            text = "Descussions",
                            color = if (switch.value == "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .clickable { switch.value = "Contacts" }
                            .fillMaxSize()
                            .weight(1f, false)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "",
                            tint = if (switch.value != "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )

                        Text(
                            text = "Contacts",
                            color = if (switch.value != "Descussions") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
    //val conversationState = conversationViewModel.conversation.value
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = { Text(text = "New Conversation") },
            text = { Text(text = "do you want create new conversation with " + contactSelected.value.fullName + " ?") },
            confirmButton = {
                Button(
                    onClick = {
//                        val contactsId = listOf<Long>(contactSelected.value.id, userId)
//                        val messagesId = listOf<Long>()
//                        conversationViewModel.newConversation(
//                            newConversation(
//                                contactsId,
//                                messagesId
//                            )
//                        )
                        conversationViewModel.newConversation(newConversation(userId,contactSelected.value.id))
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Confirm",
                        color = Color.White
                    )
                }
            }, dismissButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    }
                ) {
                    Text(
                        text = "Dismiss",
                        color = Color.White
                    )
                }
            }
        )
    }

    LaunchedEffect(conversationState) {
        when (conversationState){
            is ApiState.Success<*> ->{
                val conversation = (conversationState as ApiState.Success<Conversation>).data
                Toast.makeText(
                    context,
                    "Success to create new conversation $conversation",
                    Toast.LENGTH_LONG
                ).show()
            }
            is ApiState.Error ->{
                val error = (conversationState as ApiState.Error).message
                Toast.makeText(
                    context,
                    "Failed to create new conversation : $error",
                    Toast.LENGTH_LONG
                ).show()
            }
            else ->{

            }
        }

    }

    /*
        LaunchedEffect(conversationState) {
            if (isConversationCreated.value) return@LaunchedEffect
            when (conversationState) {
                is ApiState.Loading -> {
                }

                is ApiState.Success<*> -> {
                    val conversation = (conversationState as ApiState.Success<Conversation>).data
                    Toast.makeText(
                        context,
                        "Success to create new conversation $conversation",
                        Toast.LENGTH_LONG
                    ).show()
                    userViewModel.getUserById(userId)
                    when (userState) {
                        is ApiState.Success<*> -> {
                            val user = (userState as ApiState.Success<User>).data
                            val updatedConversationsId = user.conversationsId.toMutableList().apply {
                                add(conversation.id)
                            }
                            val newUser = user
                            newUser.conversationsId = updatedConversationsId
                            userViewModel.updateUser(newUser)
                            isConversationCreated.value = true
                        }
                        else -> {}
                    }

                }

                is ApiState.Error -> {
                    val error = (conversationState as ApiState.Error).message
                    Toast.makeText(
                        context,
                        "Failed to create new conversation : $error",
                        Toast.LENGTH_LONG
                    ).show()

                }
            }
        }
        LaunchedEffect(contactSelected.value) {
            isConversationCreated.value = false
        }
        */
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {

    }
}