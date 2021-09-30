package dev.seno.jetgithub.ui.navigation.destinations

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import com.google.accompanist.navigation.animation.composable
import dev.seno.jetgithub.ui.screens.favorite.FavoriteScreen
import dev.seno.jetgithub.ui.screens.favorite.FavoriteViewModel
import dev.seno.jetgithub.utils.Constants.DETAIL_SCREEN
import dev.seno.jetgithub.utils.Constants.FAVORITE_SCREEN
import dev.seno.jetgithub.utils.Constants.TRANSITION_DURATION

@ExperimentalAnimationApi
fun NavGraphBuilder.favoriteComposable(
    navigateToDetailScreen: (String) -> Unit,
    navigateToMainScreen: () -> Unit
) {
    composable(
        route = FAVORITE_SCREEN,
        enterTransition = { _, _ ->
            slideInVertically(
                initialOffsetY = { -it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                DETAIL_SCREEN -> slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
                else -> slideOutVertically(
                    targetOffsetY = { -it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
            }
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
    ) {
        val favoriteViewModel = hiltViewModel<FavoriteViewModel>()

        LaunchedEffect(Unit) {
            favoriteViewModel.getFavoriteListUser()
        }

        FavoriteScreen(
            navigateToDetailScreen = navigateToDetailScreen,
            navigateToMainScreen = navigateToMainScreen,
            favoriteViewModel = favoriteViewModel
        )
    }
}