package com.mohamed.tahiri.android.view.MessagingScreen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.model.Message
import com.mohamed.tahiri.android.model.newMessage
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.MessageViewModel
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessagingScreen(
    navController: NavHostController,
    messageViewModel: MessageViewModel,
    dataStoreViewModel: DataStoreViewModel,
    conversationId: Long
) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val userId by dataStoreViewModel.userId.collectAsState(-1)
    val messagesState = messageViewModel.messages.value
    val messageState = messageViewModel.message.value

    LaunchedEffect(conversationId) {
        messageViewModel.getMessagesByConversation(conversationId)
    }

    LaunchedEffect(conversationId) {
        while (true) {
            delay(2500) // Wait for 2.5 seconds
            messageViewModel.getMessagesByConversation(conversationId)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Text(conversationId.toString())
        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(1f)) {

            when (messagesState) {
                is ApiState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }

                is ApiState.Success<*> -> {
                    val messages = (messagesState as ApiState.Success<List<Message>>).data
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 8.dp),
                        reverseLayout = true
                    ) {
                        items(messages.reversed()) { message ->
                            val isFromMe =
                                message.senderId.toInt() == userId.toInt()
                            val bubbleColor = if (isFromMe) Color(0xFF4CAF50) else Color(0xFFE0E0E0)
                            val bubbleAlignment =
                                if (isFromMe) Alignment.TopEnd else Alignment.TopStart

                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp), contentAlignment = bubbleAlignment
                            ) {
                                Column(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(bubbleColor)
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = message.content,
                                        color = if (isFromMe) Color.White else Color.Black,
                                        fontSize = 16.sp
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = message.dateSending,
                                        color = if (isFromMe) Color.White.copy(alpha = 0.7f) else Color.Black.copy(
                                            alpha = 0.7f
                                        ),
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        }
                    }

                }

                is ApiState.Error -> {
                    val error = (messagesState as ApiState.Error).message
                    Text(
                        text = "Failed to fetch messages: $error",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    )
                }
            }

        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("Type a message...") }
            )
            Button(onClick = {
                if (text.isNotBlank()) {
                    messageViewModel.newMessage(
                        newMessage(
                            content = text,
                            senderId = userId,
                            dateSending = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                                .format(LocalDateTime.now()),
                            conversationId = conversationId,
                        )
                    )
                    text = ""
                    keyboardController?.hide()
                }
            }) {
                Text("Send")
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