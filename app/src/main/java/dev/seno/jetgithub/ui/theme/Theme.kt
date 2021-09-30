package dev.seno.jetgithub.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import dev.seno.jetgithub.ui.screens.main.MainViewModel

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    primaryVariant = DarkGray,
    secondary = Color.White,
    background = Color.Black,
    surface = Color.DarkGray,
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    primaryVariant = DarkGray,
    secondary = Color.Black,
    background = Color.White,
    surface = Color.White,
)

@Composable
fun JetGithubTheme(
    mainViewModel: MainViewModel,
    content: @Composable () -> Unit
) {

    val themeState by mainViewModel.darkModeState.observeAsState()
    val colors = if (themeState == true) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}