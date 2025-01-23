package com.mohamed.tahiri.android.view.ProfileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
@Composable
fun ProfileScreen(navController: NavHostController, dataStoreViewModel: DataStoreViewModel) {
    val gson = Gson()
    val userId by dataStoreViewModel.userId.collectAsState(initial = -1)
    val userFullName by dataStoreViewModel.userFullName.collectAsState(initial = "")
    val userEmail by dataStoreViewModel.userEmail.collectAsState(initial = "")
    val userPassword by dataStoreViewModel.userPassword.collectAsState(initial = "")
    val userImage by dataStoreViewModel.userImage.collectAsState(initial = "")
    val userConversations by dataStoreViewModel.userConversationsId.collectAsState(initial = "")
    Column {
        Text(text = "User ID: $userId")
        Text(text = "Full Name: $userFullName")
        Text(text = "Email: $userEmail")
        Text(text = "Password: $userPassword")
        Text(text = "Image: $userImage")
        Text(text = "Conversations: $userConversations")
        val type = object : TypeToken<List<Long>>() {}.type
        val contacts = if (userConversations.isNotEmpty()) {
            gson.fromJson(userConversations, type)
        } else {
            emptyList<Long>()
        }
        for (contact in contacts) {
            Text(text = "contact : $contact")
        }
        Button(onClick = {
            dataStoreViewModel.cleardataStoreRepository()
            navController.navigate(Screen.LoginScreen.name)
        }) {
            Text("Logout")
        }
    }
}