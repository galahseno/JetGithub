package dev.seno.jetgithub.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dev.seno.jetgithub.ui.navigation.destinations.detailComposable
import dev.seno.jetgithub.ui.navigation.destinations.favoriteComposable
import dev.seno.jetgithub.ui.navigation.destinations.mainComposable
import dev.seno.jetgithub.ui.navigation.destinations.splashComposable
import dev.seno.jetgithub.utils.Constants.DETAIL_SCREEN_NAV
import dev.seno.jetgithub.utils.Constants.FAVORITE_SCREEN
import dev.seno.jetgithub.utils.Constants.MAIN_SCREEN
import dev.seno.jetgithub.utils.Constants.SPLASH_SCREEN

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    navHostController: NavHostController
) {
    AnimatedNavHost(
        navController = navHostController,
        startDestination = SPLASH_SCREEN
    ) {
        splashComposable(
            navigateToMainScreen = {
                navHostController.navigate(MAIN_SCREEN) {
                    popUpTo(SPLASH_SCREEN) {
                        inclusive = true
                    }
                }
            }
        )
        mainComposable(
            navigateToDetailScreen = { username ->
                navHostController.navigate(DETAIL_SCREEN_NAV + username)
            },
            navigateToFavoriteScreen = {
                navHostController.navigate(FAVORITE_SCREEN)
            })
        detailComposable(
            navigateToMainScreen = {
                navHostController.popBackStack()
            },
            navigateToDetailScreen = { username ->
                navHostController.navigate(DETAIL_SCREEN_NAV + username)
            }
        )
        favoriteComposable(
            navigateToDetailScreen = { username ->
                navHostController.navigate(DETAIL_SCREEN_NAV + username)
            },
            navigateToMainScreen = {
                navHostController.popBackStack()
            }
        )
    }
}