package com.mohamed.tahiri.android.view.LoginScreen

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.model.User
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
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
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val userState = userViewModel.user.value

    var isPasswordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "ChatVista",
                fontSize = MaterialTheme.typography.titleLarge.fontSize.times(2),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.background
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Log In",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
            )
        }

        Card(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Email Field
                OutlinedTextField(
                    value = email.value,
                    onValueChange = { email.value = it },
                    label = { Text("Email Address") },
                    placeholder = { Text("Enter your email") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password.value,
                    onValueChange = { password.value = it },
                    label = { Text("Password") },
                    placeholder = { Text("Enter your password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Check else Icons.Default.Close,
                                contentDescription = if (isPasswordVisible) "Hide Password" else "Show Password"
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        userViewModel.getUser(email = email.value, password = password.value)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Log In",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Or",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.SignupScreen.name)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = "Sign Up",
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
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
                if (user.fullName.isNullOrBlank() && user.email.isNullOrBlank()) {
                    Toast.makeText(
                        context,
                        "Failed to log in: Your account does not exist!",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    dataStoreViewModel.saveUserId(user.id)
                    dataStoreViewModel.saveUserImage(user.image)
                    navController.navigate(Screen.HomeScreen.name)
                }
            }

            is ApiState.Error -> {
                val error = (userState as ApiState.Error).message
                Toast.makeText(
                    context,
                    "Failed to log in: $error",
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