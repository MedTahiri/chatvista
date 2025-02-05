package com.mohamed.tahiri.android.view.MessagingScreen

import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.R
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.ImageMapper
import com.mohamed.tahiri.android.model.Message
import com.mohamed.tahiri.android.model.newMessage
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.view.InternetProblem
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.ConversationViewModel
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.MessageViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessagingScreen(
    navController: NavHostController,
    messageViewModel: MessageViewModel,
    conversationViewModel: ConversationViewModel,
    dataStoreViewModel: DataStoreViewModel,
    conversationId: Long,
    fullname: String,
    image: String,
    admin: Long,
    context: Context
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val userId by dataStoreViewModel.userId.collectAsState(-1)
    val messagesState = messageViewModel.messages.value
    val messageState = messageViewModel.message.value

    val imageMapper = ImageMapper()

    LaunchedEffect(conversationId) {
        messageViewModel.getMessagesByConversation(conversationId)
        if (userId.toInt() != -1) {
            messageViewModel.read(conversationId, userId)
        }
    }


    LaunchedEffect(conversationId) {
        while (true) {
            delay(1500) // Wait for 2.5 seconds
            messageViewModel.getMessagesByConversation(conversationId)
            if (userId.toInt() != -1) {
                messageViewModel.read(conversationId, userId)
            }
        }
    }

    var menuVisibility by remember { mutableStateOf<Map<Long, Boolean>>(emptyMap()) }
    var menuPosition by remember { mutableStateOf(Offset.Zero) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    val imageResource = imageMapper.getImage(image) ?: R.drawable.a
                    Image(
                        painter = painterResource(id = imageResource),
                        contentDescription = "User Image",
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(24.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = fullname,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.background
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            navigationIcon = {
                IconButton(onClick = {
                    navController.navigate(Screen.HomeScreen.name)
                }) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowLeft,
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    conversationViewModel.deleteConversation(conversationId)
                    navController.navigate(Screen.HomeScreen.name)
                }, enabled = (userId == admin)) {
                    Image(
                        painter = painterResource(id = R.drawable.delete_conversation),
                        contentDescription = "Delete Conversation",
                        modifier = Modifier.size(32.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.background
            )
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            when (messagesState) {
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
                    val messages = (messagesState as ApiState.Success<List<Message>>).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        reverseLayout = true
                    ) {
                        items(messages.reversed()) { message ->
                            val isFromMe = message.senderId.toInt() == userId.toInt()
                            val bubbleColor =
                                if (isFromMe) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
                            val bubbleAlignment =
                                if (isFromMe) Alignment.TopEnd else Alignment.TopStart

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = { offset ->
                                                menuVisibility =
                                                    menuVisibility + (message.id to true)
                                                menuPosition = offset
                                            }
                                        )
                                    },
                                contentAlignment = bubbleAlignment
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(bubbleColor)
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = message.content,
                                        color = if (isFromMe) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = message.dateSending,
                                        color = if (isFromMe) MaterialTheme.colorScheme.onPrimary.copy(
                                            alpha = 0.7f
                                        ) else MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = 0.7f
                                        ),
                                        fontSize = 12.sp
                                    )
                                }
                                if (menuVisibility[message.id] == true) {
                                    DropdownMenu(
                                        expanded = true,
                                        onDismissRequest = {
                                            menuVisibility = menuVisibility - message.id
                                        },
                                        offset = DpOffset(
                                            menuPosition.x.dp,
                                            menuPosition.y.dp
                                        )
                                    ) {
                                        DropdownMenuItem(
                                            onClick = {
                                                if (isFromMe) {
                                                    messageViewModel.deleteMessage(message.id)
                                                    menuVisibility = menuVisibility - message.id
                                                } else {
                                                    Toast.makeText(
                                                        context,
                                                        "you can't delete this message !",
                                                        Toast.LENGTH_SHORT
                                                    ).show()
                                                    menuVisibility = menuVisibility - message.id
                                                }
                                            },
                                            text = {
                                                Row(
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        "Delete",
                                                        modifier = Modifier.padding(vertical = 4.dp)
                                                    )
                                                    Image(
                                                        painter = painterResource(R.drawable.delete_conversation),
                                                        contentDescription = "Delete message",
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                            .padding(vertical = 4.dp)
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                        }
                    }
                }

                is ApiState.Error -> {
                    val error = (messagesState as ApiState.Error).message
                    InternetProblem(
                        onRetry = {
                            navController.navigate(Screen.MessagingScreen.name)
                        }
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(2f)
                    .padding(end = 8.dp),
                placeholder = { Text("Type a message...") },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                )
            )

            FloatingActionButton(
                onClick = {
                    if (text.isNotBlank()) {
                        messageViewModel.newMessage(
                            newMessage(
                                content = text,
                                senderId = userId,
                                dateSending = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                    .format(LocalDateTime.now()),
                                conversationId = conversationId,
                                isRead = false
                            )
                        )
                        text = ""
                        keyboardController?.hide()
                    }
                },
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(1.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.send),
                    contentDescription = "send",
                    modifier = Modifier.size(30.dp)
                )

            }
        }
    }

    LaunchedEffect(messageState) {
        when (messageState) {
            is ApiState.Error -> {}
            is ApiState.Loading -> {}
            is ApiState.Success -> {
                messageViewModel.getMessagesByConversation(conversationId)
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun PreviewMessagingScreen() {
    AndroidTheme {
        //MessagingScreen(rememberNavController(), messageViewModel, dataStoreViewModel, -1)
    }
}