package com.mohamed.tahiri.android.view.ProfileScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    dataStoreViewModel: DataStoreViewModel
) {
    val userId by dataStoreViewModel.userId.collectAsState(initial = -1)

    userViewModel.getUserById(userId)

    val currentUser = remember {
        mutableStateOf(
            User(
                id = -1,
                fullName = "",
                email = "",
                password = "",
                image = "",
                conversationsId = mutableListOf()
            )
        )
    }
    val userState = userViewModel.user.value
    Column {
        Text(text = "User ID: $userId")
        Text(text = "Full Name: ${currentUser.value.fullName}")
        Text(text = "Email: ${currentUser.value.email}")
        Text(text = "Password: ${currentUser.value.password}")
        Text(text = "Image: ${currentUser.value.image}")
        Text(text = "Conversations: ${currentUser.value.conversationsId}")
        for (conversation in currentUser.value.conversationsId) {
            Text(text = "conversation : $conversation")
        }
        Button(onClick = {
            dataStoreViewModel.cleardataStoreRepository()
            navController.navigate(Screen.LoginScreen.name)
        }) {
            Text("Logout")
        }
    }


    LaunchedEffect(userState) {
        when (userState) {
            is ApiState.Loading -> {
            }

            is ApiState.Success<*> -> {
                val user = (userState as ApiState.Success<User>).data
                currentUser.value = user
            }

            is ApiState.Error -> {
            }
        }
    }
}