package com.mohamed.tahiri.android.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohamed.tahiri.android.R
import com.mohamed.tahiri.android.ui.theme.AndroidTheme

@Composable
fun InternetProblem(
    modifier: Modifier = Modifier,
    onRetry: () -> Unit,
    error: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Display a placeholder icon (replace with your no-internet image or icon)
        Image(
            painter = painterResource(id = R.drawable.no_internet), // Replace with your drawable resource
            contentDescription = "No Internet",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        // Title Text
        Text(
            text = "No Internet Connection",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Subtitle Text
        Text(
            text = "It looks like you're offline. Please check your connection and try again.",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Text(
            text = error,
            style = MaterialTheme.typography.bodySmall,
            fontSize = 8.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Retry Button
        Button(
            onClick = onRetry,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text(text = "Retry", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {
        InternetProblem(onRetry = {},error="")
    }
}
