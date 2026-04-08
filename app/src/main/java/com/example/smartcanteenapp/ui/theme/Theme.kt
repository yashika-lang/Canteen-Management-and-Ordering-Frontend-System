package com.example.smartcanteenapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// 🎯 TERE APP KE COLORS (clean + neutral)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6B4F3B),       // brown
    secondary = Color(0xFF8D6E63),     // light brown
    background = Color(0xFFF5F5F5),    // light background
    surface = Color(0xFFFFFFFF),       // cards
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFD7CCC8),
    secondary = Color(0xFFBCAAA4),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E1E),
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun SmartcanteenappTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}