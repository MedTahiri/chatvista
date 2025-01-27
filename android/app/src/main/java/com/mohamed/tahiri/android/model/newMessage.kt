package com.mohamed.tahiri.android.model

import java.time.LocalDateTime

data class newMessage(
    val content: String,
    val dateSending: String,
    val senderId: Long,
    val conversationId: Long
)