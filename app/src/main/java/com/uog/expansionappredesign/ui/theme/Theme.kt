package com.uog.expansionappredesign.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = purple,
    secondary = blue,
    background = dark,
    surface = csdark,
    onPrimary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)
private val LightColorScheme = lightColorScheme(
    primary = purple,
    secondary = blue,
    background = blight,
    surface = cslight,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)
/**
 * The main application theme.
 *
 * This Composable applies the custom [MaterialTheme] to the entire app.
 * It also handles the logic for switching between Light and Dark modes:
 * - Automatically detects the system setting (`isSystemInDarkTheme`).
 * - It allows manual overrides via the [darkTheme] parameter
 *   (controlled by the ViewModel).
 *
 * - Updates the Android Status Bar color to match the app's background.
 *
 * @param darkTheme Boolean flag to force dark mode (true) or light mode (false).
 * @param content The screen content to be rendered inside this theme.
 */
@Composable
fun LostInCrowdTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}