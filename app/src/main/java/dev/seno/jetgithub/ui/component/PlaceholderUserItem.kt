package dev.seno.jetgithub.ui.component

import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.shimmer
import dev.seno.jetgithub.ui.theme.LARGE_PADDING
import dev.seno.jetgithub.ui.theme.MediumGray
import dev.seno.jetgithub.ui.theme.SMALL_PADDING
import dev.seno.jetgithub.ui.theme.USER_IMAGE_SIZE

@Composable
fun PlaceholderUserItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SMALL_PADDING)
            .placeholder(
                visible = true,
                highlight = PlaceholderHighlight.shimmer(
                    highlightColor = MediumGray
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LARGE_PADDING),
        ) {
            Box(
                modifier = Modifier
                    .size(USER_IMAGE_SIZE)
            )
        }
    }
}