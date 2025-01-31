package com.mohamed.tahiri.android.view.SplashScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel
import com.mohamed.tahiri.android.viewmodel.UserViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    dataStoreViewModel: DataStoreViewModel,
    userViewModel: UserViewModel
) {
    val userId by dataStoreViewModel.userId.collectAsState(initial = -1)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "💬",
            fontSize = MaterialTheme.typography.titleLarge.fontSize.times(4),
            color = MaterialTheme.colorScheme.background
        )

        Spacer(modifier = Modifier.height(16.dp))


        var visible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(500)
            visible = true
        }

        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Text(
                text = "ChatVista",
                fontSize = MaterialTheme.typography.titleLarge.fontSize.times(2),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.background
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Connect. Chat. Share.",
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
            color = MaterialTheme.colorScheme.background.copy(alpha = 0.8f)
        )
    }

    LaunchedEffect(Unit) {
        delay(3000)
        if (userId.toInt() <= 0) {
            navController.navigate(Screen.LoginScreen.name)
        } else {
            navController.navigate(Screen.HomeScreen.name)
        }
    }
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {

    }
}