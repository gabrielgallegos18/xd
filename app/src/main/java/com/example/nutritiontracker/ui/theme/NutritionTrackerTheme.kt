package com.example.nutritiontracker.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define colors for the nutrition app
val Green200 = Color(0xFFA5D6A7)
val Green500 = Color(0xFF4CAF50)
val Green700 = Color(0xFF388E3C)
val Orange200 = Color(0xFFFFCC80)
val Orange500 = Color(0xFFFF9800)
val Orange700 = Color(0xFFF57C00)
val LightGray = Color(0xFFF5F5F5)
val DarkGray = Color(0xFF616161)
val Red500 = Color(0xFFF44336)

private val DarkColorScheme = darkColorScheme(
    primary = Green500,
    secondary = Orange500,
    tertiary = Green200,
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

private val LightColorScheme = lightColorScheme(
    primary = Green500,
    secondary = Orange500,
    tertiary = Green700,
    background = LightGray,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = DarkGray,
    onSurface = DarkGray,
)

@Composable
fun NutritionTrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}