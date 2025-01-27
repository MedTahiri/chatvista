package com.mohamed.tahiri.android.model

import java.time.LocalDateTime

data class Message(
    val id: Long,
    val content: String,
    val dateSending: String,
    val senderId: Long,
    val conversationId: Long
)