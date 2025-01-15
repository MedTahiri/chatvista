package com.mohamed.tahiri.android.model

import retrofit2.http.GET

interface ApiService {
    @GET("user/users")
    suspend fun getUsers(): List<User>
}