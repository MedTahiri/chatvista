package com.mohamed.tahiri.android.view.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.mohamed.tahiri.android.Screen
import com.mohamed.tahiri.android.ui.theme.AndroidTheme
import com.mohamed.tahiri.android.viewmodel.DataStoreViewModel

@Composable
fun SplashScreen(navController: NavHostController, dataStoreViewModel: DataStoreViewModel) {
    val userId by dataStoreViewModel.userId.collectAsState(initial = -1)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "ChatVista",
            fontSize = MaterialTheme.typography.titleLarge.fontSize.times(2),
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.background
        )
    }
    android.os.Handler().postDelayed({
        if (userId.toInt() != -1){
            navController.navigate(Screen.HomeScreen.name)
        }else{
            navController.navigate(Screen.LoginScreen.name)
        }


    }, 3000)
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {

    }
}