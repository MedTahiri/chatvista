package com.mohamed.tahiri.android

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.view.HomeScreen.HomeScreen
import com.mohamed.tahiri.android.view.LoginScreen.LoginScreen
import com.mohamed.tahiri.android.view.MessagingScreen.MessagingScreen
import com.mohamed.tahiri.android.view.ProfileScreen.ProfileScreen
import com.mohamed.tahiri.android.view.SignupScreen.SignupScreen
import com.mohamed.tahiri.android.view.SplashScreen.SplashScreen
import com.mohamed.tahiri.android.viewmodel.ConversationViewModel
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.MessageViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val userViewModel: UserViewModel by viewModels()
    private val dataStoreViewModel: DataStoreViewModel by viewModels()
    private val conversationViewModel: ConversationViewModel by viewModels()
    private val messageViewModel: MessageViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AndroidTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main(userViewModel, dataStoreViewModel, conversationViewModel, messageViewModel)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Main(
    userViewModel: UserViewModel,
    dataStoreViewModel: DataStoreViewModel,
    conversationViewModel: ConversationViewModel,
    messageViewModel: MessageViewModel
) {
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SplashScreen.name) {
        composable(Screen.SplashScreen.name) {
            SplashScreen(
                navController,
                dataStoreViewModel
            )
        }
        composable(Screen.SignupScreen.name) {
            SignupScreen(
                navController,
                userViewModel,
                dataStoreViewModel,
                context
            )
        }
        composable(Screen.LoginScreen.name) {
            LoginScreen(
                navController,
                userViewModel,
                dataStoreViewModel,
                context
            )
        }
        composable(Screen.HomeScreen.name) {
            HomeScreen(
                navController,
                userViewModel,
                dataStoreViewModel,
                conversationViewModel,
                context
            )
        }
        composable(
            Screen.MessagingScreen.name + "/{conversationId}/{fullname}/{image}",
            arguments = listOf(
                navArgument("conversationId") { type = NavType.LongType },
                navArgument("fullname") { type = NavType.StringType },
                navArgument("image") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val conversationId = backStackEntry.arguments?.getLong("conversationId") ?: -1
            val fullname = backStackEntry.arguments?.getString("fullname") ?: ""
            val image = backStackEntry.arguments?.getString("image") ?: "0"
            MessagingScreen(
                navController,
                messageViewModel,
                conversationViewModel,
                dataStoreViewModel,
                conversationId,
                fullname,
                image
            )
        }
        composable(Screen.ProfileScreen.name) {
            ProfileScreen(
                navController,
                userViewModel,
                dataStoreViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidTheme {

    }
}