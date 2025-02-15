package com.mohamed.tahiri.android.model

import retrofit2.http.Body
import retrofit2.http.DELETE
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
    suspend fun newConversation(@Body conversation: newConversation): ConversationTitle

    @GET("conversation/{id}")
    suspend fun getConversationById(@Path("id") id: Long): Conversation

    @GET("conversation/my/{userid}")
    suspend fun getConversationByUser(@Path("userid") userId: Long): List<ConversationTitle>

    @POST("message/new")
    suspend fun newMessage(@Body message: newMessage): Message

    @GET("message/my/{conversationId}")
    suspend fun getMessagesByConversation(@Path("conversationId") conversationId: Long): List<Message>

    @DELETE("user/delete/{id}")
    suspend fun deleteUser(@Path("id") id: Long)

    @DELETE("message/delete/{id}")
    suspend fun deleteMessage(@Path("id") id: Long)

    @DELETE("conversation/delete/{id}")
    suspend fun deleteConversation(@Path("id") id: Long)

    @GET("user/find/{text}")
    suspend fun findUsers(@Path("text") text : String) : List<User>

    @GET("conversation/find/{userId}/{text}")
    suspend fun findConversation(@Path("userId") userId: Long , @Path("text") text: String) : List<ConversationTitle>

    @PUT("message/read/{conversation}/{currentuser}")
    suspend fun read(@Path("conversation") conversation: Long,@Path("currentuser") currentuser:Long)

}