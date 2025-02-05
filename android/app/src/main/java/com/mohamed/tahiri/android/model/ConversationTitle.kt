package com.mohamed.tahiri.android.model

data class ConversationTitle(
    val id: Long,
    val fullName: String,
    val lastMessage: String,
    val time: String,
    val image: String,
    val admin: Long,
    val isAllRead: Boolean,
    val lastSender:Long
)