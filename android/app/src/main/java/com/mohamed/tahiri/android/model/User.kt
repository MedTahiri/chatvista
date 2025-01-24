package com.mohamed.tahiri.android.model

class User(
    val id: Long,
    val fullName: String,
    val email: String,
    val password: String,
    val image:String,
    val conversationsId:MutableList<Long>
)

