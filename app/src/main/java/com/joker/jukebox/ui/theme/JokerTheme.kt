package com.joker.jukebox.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Joker Color Palette
val JokerPurple = Color(0xFF2E0854)
val JokerPurpleDark = Color(0xFF1A0033)
val NeonGreen = Color(0xFF00FF41)
val JokerRed = Color(0xFFD90000)
val DarkBackground = Color(0xFF121212)

private val DarkColorScheme = darkColorScheme(
    primary = NeonGreen,
    onPrimary = DarkBackground,
    primaryContainer = JokerPurple,
    secondary = JokerRed,
    background = DarkBackground,
    surface = JokerPurpleDark,
    onSurface = Color.White,
    error = JokerRed
)

@Composable
fun JokerTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = androidx.compose.material3.Typography(),
        content = content
    )
}