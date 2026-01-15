package com.meifei.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColors = lightColorScheme(
    primary = Color(0xFF2E7D32),
    secondary = Color(0xFF0288D1),
    surface = Color(0xFFF7F7F7)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF81C784),
    secondary = Color(0xFF4FC3F7)
)

@Composable
fun MeiFeiTheme(content: @Composable () -> Unit) {
    val view = LocalView.current
    SideEffect {
        WindowCompat.setDecorFitsSystemWindows(view.context.findActivity().window, false)
    }
    MaterialTheme(
        colorScheme = if (androidx.compose.foundation.isSystemInDarkTheme()) DarkColors else LightColors,
        content = content
    )
}

private fun android.content.Context.findActivity(): android.app.Activity {
    var context = this
    while (context is android.content.ContextWrapper) {
        if (context is android.app.Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("Activity not found")
}
