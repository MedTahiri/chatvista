package com.mohamed.tahiri.android.model

data class newMessage(
    val content: String,
    val dateSending: String,
    val senderId: Long,
    val conversationId: Long,
    val isRead:Boolean
)