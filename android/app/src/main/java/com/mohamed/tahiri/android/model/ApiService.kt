package com.mohamed.tahiri.android.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("user/users")
    suspend fun getUsers(): List<User>

    @POST("user/new")
    suspend fun newUser(@Body user: newUser) : User
}