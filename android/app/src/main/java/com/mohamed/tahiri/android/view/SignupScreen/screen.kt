package com.mohamed.tahiri.android.view.SignupScreen

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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import com.mohamed.tahiri.android.R
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.model.newUser
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.view.MyTextField
import com.mohamed.tahiri.android.viewmodel.ApiState
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel
import kotlin.random.Random

@Composable
fun SignupScreen(
    navController: NavHostController,
    userViewModel: UserViewModel,
    dataStoreViewModel: DataStoreViewModel,
    context: Context
) {
    val userState = userViewModel.user.value
    val name = remember {
        mutableStateOf("")
    }
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
    val confirmepassword = remember {
        mutableStateOf("")
    }

    val i = Random.nextInt(0, 10)

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
                text = "Create an Account",
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
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                MyTextField(name, "Full Name", "Full Name", KeyboardOptions.Default)
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
                MyTextField(
                    confirmepassword,
                    "Confirm Password",
                    "Confirm Password",
                    KeyboardOptions(keyboardType = KeyboardType.Password)
                )
                Button(
                    onClick = {
                        if (password.value == confirmepassword.value) {
                            val user = newUser(name.value, email.value, password.value, i.toString())

                            userViewModel.createUser(user)

                        } else {
                            Toast.makeText(
                                context,
                                "Failed to create new user passwords do not match ! ",
                                Toast.LENGTH_LONG
                            ).show()
                        }

                    }, modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Create Account")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Already have an account?")
                    TextButton(onClick = {
                        navController.navigate(Screen.LoginScreen.name)
                    }) {
                        Text(text = "Login Here")
                    }
                }
            }
        }
    }

    LaunchedEffect(userState) {
        when (userState) {
            is ApiState.Loading -> {
            }

            is ApiState.Success<*> -> {
                val user = (userState as ApiState.Success<User>).data
                Toast.makeText(
                    context,
                    "Success to create new user : $user",
                    Toast.LENGTH_LONG
                ).show()
                dataStoreViewModel.saveUserId(user.id)
                navController.navigate(Screen.HomeScreen.name)
            }

            is ApiState.Error -> {
                val error = (userState as ApiState.Error).message
                Toast.makeText(
                    context,
                    "Failed to create new user : $error",
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