package dev.seno.jetgithub.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.seno.jetgithub.R
import dev.seno.jetgithub.ui.screens.main.MainViewModel
import dev.seno.jetgithub.ui.theme.LOTTIE_IMAGE_SIZE
import dev.seno.jetgithub.utils.Constants.SPLASH_SCREEN_DELAY
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel,
    navigateToMainScreen: () -> Unit
) {
    var startAnimation by remember { mutableStateOf(false) }
    val offsetValue by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else (-100).dp,
        animationSpec = tween(durationMillis = 1500)
    )
    val alphaValue by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1500)
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(SPLASH_SCREEN_DELAY)
        navigateToMainScreen()
    }
    val themeState by mainViewModel.darkModeState.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary),
        contentAlignment = Alignment.Center
    ) {
        if (themeState == true) {
            val compositionLight by
            rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.github_icon_white))
            val progressLight by animateLottieCompositionAsState(compositionLight)

            LottieAnimation(
                modifier = Modifier
                    .size(LOTTIE_IMAGE_SIZE)
                    .offset(y = offsetValue)
                    .alpha(alphaValue),
                composition = compositionLight,
                progress = progressLight
            )
        } else {
            val compositionDark by
            rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.github_icon_black))
            val progressDark by animateLottieCompositionAsState(compositionDark)

            LottieAnimation(
                modifier = Modifier
                    .size(LOTTIE_IMAGE_SIZE)
                    .offset(y = offsetValue)
                    .alpha(alphaValue),
                composition = compositionDark,
                progress = progressDark
            )
        }
    }
}