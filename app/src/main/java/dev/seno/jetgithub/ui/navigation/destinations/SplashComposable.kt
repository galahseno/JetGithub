package dev.seno.jetgithub.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import dev.seno.jetgithub.ui.screens.main.MainViewModel
import dev.seno.jetgithub.ui.screens.splash.SplashScreen
import dev.seno.jetgithub.utils.Constants.SPLASH_SCREEN
import dev.seno.jetgithub.utils.Constants.TRANSITION_DURATION

@ExperimentalAnimationApi
fun NavGraphBuilder.splashComposable(
    navigateToMainScreen: () -> Unit
) {
    composable(
        route = SPLASH_SCREEN,
        exitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
    ) {
        val mainViewModel = hiltViewModel<MainViewModel>()

        SplashScreen(
            mainViewModel = mainViewModel,
            navigateToMainScreen = navigateToMainScreen
        )
    }
}