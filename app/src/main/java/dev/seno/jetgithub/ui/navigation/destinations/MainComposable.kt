package dev.seno.jetgithub.ui.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import dev.seno.jetgithub.ui.screens.main.MainScreen
import dev.seno.jetgithub.ui.screens.main.MainViewModel
import dev.seno.jetgithub.utils.Constants.FAVORITE_SCREEN
import dev.seno.jetgithub.utils.Constants.MAIN_SCREEN
import dev.seno.jetgithub.utils.Constants.TRANSITION_DURATION


@ExperimentalAnimationApi
fun NavGraphBuilder.mainComposable(
    navigateToDetailScreen: (String) -> Unit,
    navigateToFavoriteScreen: () -> Unit
) {
    composable(
        route = MAIN_SCREEN,
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                FAVORITE_SCREEN -> slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
                else -> slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
            }
        },
        popEnterTransition = { initial, _ ->
            when (initial.destination.route) {
                FAVORITE_SCREEN -> slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
                else -> slideInHorizontally(
                    initialOffsetX = { -it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
            }
        }
    ) {
        val mainViewModel = hiltViewModel<MainViewModel>()

        MainScreen(
            navigateToDetail = navigateToDetailScreen,
            mainViewModel = mainViewModel,
            navigateToFavoriteScreen = navigateToFavoriteScreen
        )
    }
}