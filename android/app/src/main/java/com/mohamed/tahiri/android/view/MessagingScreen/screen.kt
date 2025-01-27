package com.mohamed.tahiri.android.view.MessagingScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mohamed.tahiri.android.model.message
import com.mohamed.tahiri.android.model.newMessage
import com.mohamed.tahiri.android.ui.theme.AndroidTheme

@Composable
fun MessagingScreen(navController: NavHostController) {
    var text by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val messages = remember { mutableStateListOf<newMessage>() } // Fix: Use remember to persist messages
    val currentUser = 0 // Fix: Define the current user

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Message List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(horizontal = 8.dp),
            reverseLayout = true // Show latest messages at the bottom
        ) {
            items(messages.reversed()) { message ->
                val isFromMe = message.senderId.toInt() == currentUser // Fix: Determine if the message is from the current user
                val bubbleColor = if (isFromMe) Color(0xFF4CAF50) else Color(0xFFE0E0E0)
                val bubbleAlignment = if (isFromMe) Alignment.End else Alignment.Start

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
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
                            text = message.senderId.toString(),
                            color = if (isFromMe) Color.White.copy(alpha = 0.7f) else Color.Black.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }
                }
            }
        }

        // Message Input
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
                    // Fix: Add the new message to the list
                    messages.add(
                        newMessage(
                            content = text,
                            senderId = 1,
                            conversationId = 1,
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
}

@Preview(showBackground = true)
@Composable
fun PreviewMessagingScreen() {
    AndroidTheme {
        MessagingScreen(rememberNavController())
    }
}