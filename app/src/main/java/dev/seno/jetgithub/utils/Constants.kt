package dev.seno.jetgithub.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.People
import dev.seno.jetgithub.data.model.DetailTab

object Constants {

    const val SETTING_PREF = "dark_mode"
    const val SETTING_KEY_PREF = "dark_mode_key"

    const val SPLASH_SCREEN = "splash"
    const val SPLASH_SCREEN_DELAY = 3500L
    const val TRANSITION_DURATION = 800

    const val MAIN_SCREEN = "main"
    const val FAVORITE_SCREEN = "favorite"

    const val DETAIL_SCREEN = "detail/{username}"
    const val DETAIL_SCREEN_NAV = "detail/"
    const val DETAIL_ARGUMENT = "username"
    const val DEFAULT_DETAIL_SCREEN_TITLE = "Detail Screen"

    const val BASE_URL = "https://api.github.com/"

    const val FAVORITE_TABLE_NAME = "favorite_user"
    const val FAVORITE_DB_NAME = "favorite_db"

    fun getDetailTab(): List<DetailTab> {
        return listOf(
            DetailTab(
                "Following",
                Icons.Filled.People,
                "Following Icon"
            ),
            DetailTab(
                "Follower",
                Icons.Filled.People,
                "Follower Icon"
            ),
        )
    }
}