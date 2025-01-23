package com.mohamed.tahiri.android.view.LoginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.gson.Gson
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.view.MyTextField
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    dataStoreViewModel: DataStoreViewModel,
    context: Context
) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val userState = userViewModel.user.value
    val gson = Gson()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f, false), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Log In",
                fontSize = MaterialTheme.typography.titleLarge.fontSize.times(2),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
        }
        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f, false), colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ), shape = RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                MyTextField(
                    email,
                    "Email Address",
                    "Email Address",
                    KeyboardOptions(keyboardType = KeyboardType.Email)
                )
                MyTextField(
                    password,
                    "Password",
                    "Password",
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Button(
                    onClick = {
                        userViewModel.getUser(email = email.value, password = password.value)
                        //navController.navigate(Screen.HomeScreen.name)
                    }, modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(1f), shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Log In")
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Or")
                }
                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.SignupScreen.name)
                    }, modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(1f), shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "SignUp")
                }
            }
        }
    }
    LaunchedEffect(userState) {
        when (userState) {
            is ApiState.Loading -> {
//                Toast.makeText(
//                    context,
//                    "wait ...",
//                    Toast.LENGTH_SHORT
//                ).show()
            }

            is ApiState.Success<*> -> {
                val user = (userState as ApiState.Success<User>).data
                Toast.makeText(
                    context,
                    "Success to log in",
                    Toast.LENGTH_LONG
                ).show()
                dataStoreViewModel.saveUserId(user.id)
                dataStoreViewModel.saveUserFullName(user.fullName)
                dataStoreViewModel.saveUserEmail(user.email)
                dataStoreViewModel.saveUserPassword(user.password)
                dataStoreViewModel.saveUserImage(user.image)
                dataStoreViewModel.saveUserConversationsId(gson.toJson(user.conversationsId))
                navController.navigate(Screen.HomeScreen.name)
            }

            is ApiState.Error -> {
                val error = (userState as ApiState.Error).message
                Toast.makeText(
                    context,
                    "Failed to log in : $error",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}


@Preview
@Composable
fun GreetingPreview() {
    AndroidTheme {

    }
}