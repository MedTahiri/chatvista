package com.mohamed.tahiri.android.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

/*
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

 */
val LightColorScheme = lightColorScheme(
    primary = Color(0xFF2196F3), // Green
    secondary = Color(0xFF64b5F6), // Blue
    background = Color(0xFFFFFFFF), // White
    surface = Color(0xFFF5F5F5), // Light Gray
    onPrimary = Color(0xFFFFFFFF), // White
    onSecondary = Color(0xFFFFFFFF), // White
    onBackground = Color(0xFF000000), // Black
    onSurface = Color(0xFF000000), // Black
    error = Color(0xFFFF5252) // Red
)

val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF2196F3), // Green
    secondary = Color(0xFF64b5F6), // Blue
    background = Color(0xFF121212), // Dark Gray
    surface = Color(0xFF1E1E1E), // Dark Surface
    onPrimary = Color(0xFFFFFFFF), // White
    onSecondary = Color(0xFFFFFFFF), // White
    onBackground = Color(0xFFFFFFFF), // White
    onSurface = Color(0xFFFFFFFF), // White
    error = Color(0xFFFF5252) // Red
)
@Composable
fun AndroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}