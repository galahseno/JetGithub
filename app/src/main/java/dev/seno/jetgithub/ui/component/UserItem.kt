package dev.seno.jetgithub.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import dev.seno.jetgithub.R
import dev.seno.jetgithub.data.model.User
import dev.seno.jetgithub.ui.theme.*

@OptIn(ExperimentalCoilApi::class)
@Composable
fun UserItem(
    userName: User,
    navigateToDetailScreen: (String) -> Unit,
) {
    val painter = rememberImagePainter(
        data = userName.userImg,
        builder = {
            crossfade(300)
        })

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToDetailScreen(userName.username)
            }
            .padding(SMALL_PADDING),
        elevation = USER_ITEM_ELEVATION,
        shape = RoundedCornerShape(ROUNDED_CORNER_SIZE)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.surface)
                .padding(LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(USER_IMAGE_SIZE)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                when (painter.state) {
                    is ImagePainter.State.Loading -> CircularProgressIndicator(
                        color = MaterialTheme.colors.secondary
                    )
                    is ImagePainter.State.Error -> Icon(
                        imageVector = Icons.Filled.BrokenImage,
                        contentDescription = stringResource(id = R.string.broken_image)
                    )
                    else -> Image(
                        painter = painter,
                        contentScale = ContentScale.Crop,
                        contentDescription = stringResource(id = R.string.user_image)
                    )
                }
            }
            Spacer(modifier = Modifier.width(LARGE_PADDING))
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = LARGE_PADDING),
                text = userName.username,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewUserItem() {
    UserItem(
        userName = User(
            userImg = "",
            username = "Name",
            id= 1
        ),
        navigateToDetailScreen = {}
    )
}