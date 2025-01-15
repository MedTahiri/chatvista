package com.mohamed.tahiri.android.view.SignupScreen

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
fun SignupScreen(navController: NavHostController) {
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
            Column (modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
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
                        navController.navigate(Screen.HomeScreen.name)
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
}


@Preview
@Composable
fun GreetingPreview() {
    AndroidTheme {
        SignupScreen(navController = rememberNavController())
    }
}