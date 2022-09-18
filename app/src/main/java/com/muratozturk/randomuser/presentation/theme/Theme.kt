package com.muratozturk.randomuser.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = DarkPrimaryColor,
    secondary = ToolTip,
    primaryVariant = DarkTextColor,
    background = DarkLightColor
)

private val LightColorPalette = lightColors(
    primary = PrimaryColor,
    secondary = ToolTip,
    primaryVariant = TextColor,
    background = LightColor

)

@Composable
fun RandomUserTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}