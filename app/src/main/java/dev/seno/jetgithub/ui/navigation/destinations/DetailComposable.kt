package dev.seno.jetgithub.ui.navigation.destinations

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.navArgument
import com.google.accompanist.navigation.animation.composable
import dev.seno.jetgithub.ui.screens.detail.DetailScreen
import dev.seno.jetgithub.ui.screens.detail.DetailViewModel
import dev.seno.jetgithub.utils.Constants.DEFAULT_DETAIL_SCREEN_TITLE
import dev.seno.jetgithub.utils.Constants.DETAIL_ARGUMENT
import dev.seno.jetgithub.utils.Constants.DETAIL_SCREEN
import dev.seno.jetgithub.utils.Constants.TRANSITION_DURATION

@ExperimentalAnimationApi
fun NavGraphBuilder.detailComposable(
    navigateToMainScreen: () -> Unit,
    navigateToDetailScreen: (String) -> Unit
) {
    composable(
        route = DETAIL_SCREEN,
        arguments = listOf(
            navArgument(DETAIL_ARGUMENT) {
                type = NavType.StringType
            }
        ),
        enterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        },
        exitTransition = { _, target ->
            when (target.destination.route) {
                DETAIL_SCREEN -> slideOutHorizontally(
                    targetOffsetX = { -it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
                else -> slideOutHorizontally(
                    targetOffsetX = { it },
                    animationSpec = tween(TRANSITION_DURATION)
                )
            }
        },
        popEnterTransition = { _, _ ->
            slideInHorizontally(
                initialOffsetX = { -it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        },
        popExitTransition = { _, _ ->
            slideOutHorizontally(
                targetOffsetX = { it },
                animationSpec = tween(TRANSITION_DURATION)
            )
        }
    ) { entry ->
        val argument = entry.arguments?.getString(DETAIL_ARGUMENT)
        val detailViewModel = hiltViewModel<DetailViewModel>()

        LaunchedEffect(key1 = argument) {
            if (argument != null) {
                detailViewModel.getDetailUser(argument)
            }
        }

        DetailScreen(
            username = argument ?: DEFAULT_DETAIL_SCREEN_TITLE,
            navigateToMainScreen = navigateToMainScreen,
            detailViewModel = detailViewModel,
            navigateToDetailScreen = navigateToDetailScreen
        )
    }
}