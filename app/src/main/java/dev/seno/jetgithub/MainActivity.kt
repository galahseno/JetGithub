package dev.seno.jetgithub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.livedata.observeAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import dev.seno.jetgithub.ui.navigation.Navigation
import dev.seno.jetgithub.ui.screens.main.MainViewModel
import dev.seno.jetgithub.ui.theme.JetGithubTheme

@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetGithubTheme(mainViewModel = mainViewModel) {
                val navHostController = rememberAnimatedNavController()
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = mainViewModel.darkModeState.observeAsState(false)
                val systemBarColor = MaterialTheme.colors.primary

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = systemBarColor,
                        darkIcons = !useDarkIcons.value
                    )
                }
                Navigation(navHostController = navHostController)
            }
        }
    }
}