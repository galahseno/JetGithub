package dev.seno.jetgithub.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.seno.jetgithub.R
import dev.seno.jetgithub.ui.theme.LOTTIE_IMAGE_SIZE

@Composable
fun IdleState(isDarkMode: Boolean?) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (isDarkMode == true) {
            val compositionLight by
            rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.github_icon_white))
            val progressLight by animateLottieCompositionAsState(compositionLight)

            LottieAnimation(
                modifier = Modifier.size(LOTTIE_IMAGE_SIZE),
                composition = compositionLight,
                progress = progressLight
            )
        } else {
            val compositionDark by
            rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.github_icon_black))
            val progressDark by animateLottieCompositionAsState(compositionDark)

            LottieAnimation(
                modifier = Modifier.size(LOTTIE_IMAGE_SIZE),
                composition = compositionDark,
                progress = progressDark
            )
        }
    }
}