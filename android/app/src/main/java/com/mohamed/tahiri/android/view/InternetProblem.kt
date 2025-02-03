package com.mohamed.tahiri.android.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
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
    onRetry: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.no_internet),
            contentDescription = "No Internet",
            modifier = Modifier
                .size(150.dp)
                .padding(bottom = 16.dp)
        )

        Text(
            text = "No Internet Connection",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "It looks like you're offline. Please check your connection and try again.",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        IconButton(
            onClick = onRetry
        ) {
            Image(
                painter = painterResource(R.drawable.retry),
                contentDescription = "retry",
                modifier = Modifier
                    .size(50.dp)
            )
        }
    }
}

@Composable
@Preview
fun Preview() {
    AndroidTheme {

    }
}
