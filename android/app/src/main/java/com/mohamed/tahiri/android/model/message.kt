package com.mohamed.tahiri.android.model

import java.time.LocalDateTime

data class message(
    val id: Long,
    val content: String,
    val dateSending: LocalDateTime,
    val senderId: Long,
    val conversationId: Long
)