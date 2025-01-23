package com.mohamed.tahiri.android.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("user/users")
    suspend fun getUsers(): List<User>

    @POST("user/new")
    suspend fun newUser(@Body user: newUser) : User

    @GET("user/{email}/{password}")
    suspend fun getUser(@Path("email") email:String , @Path("password") password:String) : User
}