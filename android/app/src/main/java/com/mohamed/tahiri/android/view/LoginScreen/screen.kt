package com.mohamed.tahiri.android.view.LoginScreen

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.view.MyTextField
import com.mohamed.tahiri.android.ui.theme.AndroidTheme

@Composable
fun LoginScreen(navController: NavHostController) {
    val email = remember {
        mutableStateOf("")
    }
    val password = remember {
        mutableStateOf("")
    }
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
                .weight(1f, false)
            , horizontalAlignment = Alignment.CenterHorizontally,
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
            )
            , shape = RoundedCornerShape(30.dp,30.dp,0.dp,0.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxSize().padding(16.dp)
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
                        navController.navigate(Screen.HomeScreen.name)
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
}


@Preview
@Composable
fun GreetingPreview() {
    AndroidTheme {
        LoginScreen(navController = rememberNavController())
    }
}