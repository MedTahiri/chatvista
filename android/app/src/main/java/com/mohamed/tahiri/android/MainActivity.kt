package com.mohamed.tahiri.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.view.HomeScreen.HomeScreen
import com.mohamed.tahiri.android.view.LoginScreen.LoginScreen
import com.mohamed.tahiri.android.view.MessagingScreen.MessagingScreen
import com.mohamed.tahiri.android.view.ProfileScreen.ProfileScreen
import com.mohamed.tahiri.android.view.SignupScreen.SignupScreen
import com.mohamed.tahiri.android.view.SplashScreen.SplashScreen
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel

import com.mohamed.tahiri.android.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val dataStoreViewModel : DataStoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(userViewModel,dataStoreViewModel)
                }
            }
        }
    }
}

@Composable
fun Main(userViewModel: UserViewModel, dataStoreViewModel: DataStoreViewModel) {
    val Context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.name) {
        composable(Screen.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(Screen.SignupScreen.name) {

            SignupScreen(navController, userViewModel,dataStoreViewModel, Context)
        }
        composable(Screen.LoginScreen.name) {
            LoginScreen(navController)
        }
        composable(Screen.HomeScreen.name) {
            HomeScreen(navController, userViewModel)
        }
        composable(Screen.MessagingScreen.name) {
            MessagingScreen(navController)
        }
        composable(Screen.ProfileScreen.name) {
            ProfileScreen(navController, dataStoreViewModel)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTheme {

    }
}