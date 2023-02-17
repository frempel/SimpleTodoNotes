package com.example.simpletodo.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorPalette = darkColorScheme(
        primary = Purple200,
        secondary = Teal200
)

private val LightColorPalette = lightColorScheme(
        primary = Purple500,
        secondary = Teal200

        /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun SimpleTodoTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColour: Boolean = true,
    content: @Composable () -> Unit
) {
    val colours = if (isDynamicColour && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (isDarkTheme)
            dynamicDarkColorScheme(LocalContext.current)
        else
            dynamicLightColorScheme(LocalContext.current)
    }
    else {
        if (isDarkTheme)
            DarkColorPalette
        else
            LightColorPalette
    }

    MaterialTheme(
            colorScheme = colours,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}