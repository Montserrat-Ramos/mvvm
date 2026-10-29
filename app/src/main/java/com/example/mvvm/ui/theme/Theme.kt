package com.example.mvvm.ui.theme

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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(

    primary = PortalGreen,

    secondary = PortalTurquoise,

    background = SurfaceLight,

    surface = SurfaceCard,

    surfaceVariant = Color(0xFFE8EEEA),

    onPrimary = PortalDark,

    onSecondary = PortalDark,

    onBackground = TextPrimary,

    onSurface = TextPrimary,

    onSurfaceVariant = TextSecondary
)

@Composable
fun MvvmTheme(
    content: @Composable () -> Unit
) {

    val colorScheme = LightColorScheme

    val view = LocalView.current

    if (!view.isInEditMode) {

        val window = (view.context as Activity).window

        window.statusBarColor =
            colorScheme.background.toArgb()

        WindowCompat.getInsetsController(
            window,
            view
        ).isAppearanceLightStatusBars = true
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}