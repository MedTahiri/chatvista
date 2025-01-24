package com.mohamed.tahiri.android.model

data class Conversation(
    val id: Long,
    val contactsId: List<Long>,
    val messagesId: List<Long>
)
