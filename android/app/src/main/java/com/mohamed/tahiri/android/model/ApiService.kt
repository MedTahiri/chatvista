package com.mohamed.tahiri.android.model

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("user/users")
    suspend fun getUsers(): List<User>

    @POST("user/new")
    suspend fun newUser(@Body user: newUser): User

    @GET("user/{email}/{password}")
    suspend fun getUser(@Path("email") email: String, @Path("password") password: String): User

    @PUT("user/update")
    suspend fun updateUser(@Body user: User): User

    @GET("user/{id}")
    suspend fun getUserById(@Path("id") id: Long): User


    @POST("conversation/new")
    suspend fun newConversation(@Body conversation: newConversation): Conversation

    @GET("conversation/{id}")
    suspend fun getConversationById(@Path("id") id: Long): Conversation

    @GET("conversation/my/{userid}")
    suspend fun getConversationByUser(@Path("userid") userId: Long): List<ConversationTitle>

    @POST("message/new")
    suspend fun newMessage(@Body message: newMessage): Message

    @GET("message/my/{conversationId}")
    suspend fun getMessagesByConversation(@Path("conversationId") conversationId: Long): List<Message>
}