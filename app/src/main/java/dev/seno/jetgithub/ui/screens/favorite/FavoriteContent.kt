package dev.seno.jetgithub.ui.screens.favorite

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import dev.seno.jetgithub.R
import dev.seno.jetgithub.data.model.FavoriteUser
import dev.seno.jetgithub.ui.component.DisplayEmptyContentOrError
import dev.seno.jetgithub.ui.component.UserItem
import dev.seno.jetgithub.ui.screens.main.PlaceholderUserList
import dev.seno.jetgithub.ui.theme.LARGEST_PADDING
import dev.seno.jetgithub.ui.theme.ROUNDED_CORNER_SIZE
import dev.seno.jetgithub.ui.theme.SMALL_PADDING
import dev.seno.jetgithub.utils.DisplayState
import dev.seno.jetgithub.utils.Resource

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FavoriteContent(
    listFavorite: Resource<List<FavoriteUser>>,
    navigateToDetailScreen: (String) -> Unit,
    onSwipeToDelete: @Composable (FavoriteUser) -> Unit
) {
    when (listFavorite) {
        is Resource.Success -> {
            UserFavoriteItem(
                listFavorite = listFavorite.data,
                navigateToDetailScreen = navigateToDetailScreen,
                onSwipeToDelete = onSwipeToDelete
            )
        }
        is Resource.Loading -> PlaceholderUserList()
        else -> DisplayEmptyContentOrError(DisplayState.Error)
    }
}

@ExperimentalAnimationApi
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun UserFavoriteItem(
    listFavorite: List<FavoriteUser>,
    navigateToDetailScreen: (String) -> Unit,
    onSwipeToDelete: @Composable (FavoriteUser) -> Unit
) {
    if (listFavorite.isEmpty()) {
        DisplayEmptyContentOrError(DisplayState.Favorite)
    } else {
        LazyColumn(
            modifier = Modifier.padding(SMALL_PADDING)
        ) {
            items(
                items = listFavorite,
                key = {
                    it.id
                }
            ) { item ->
                var isDeleted by remember { mutableStateOf(false) }
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if (it == DismissValue.DismissedToEnd) isDeleted =
                            !isDeleted
                        else if (it == DismissValue.DismissedToStart) isDeleted =
                            !isDeleted
                        it != DismissValue.DismissedToStart || it != DismissValue.DismissedToEnd
                    }
                )
                val degrees by animateFloatAsState(
                    when (dismissState.targetValue) {
                        DismissValue.Default -> 0f
                        DismissValue.DismissedToStart -> -45f
                        DismissValue.DismissedToEnd -> 45f
                    }
                )

                val dismissDirection = dismissState.dismissDirection
                val isDismissedStart = dismissState.isDismissed(DismissDirection.StartToEnd)
                val isDismissedEnd = dismissState.isDismissed(DismissDirection.EndToStart)
                if (isDeleted) {
                    onSwipeToDelete(item)
                }

                var itemAppeared by remember { mutableStateOf(false) }
                LaunchedEffect(key1 = true) {
                    itemAppeared = true
                }

                AnimatedVisibility(
                    visible = itemAppeared && !isDismissedEnd && !isDismissedStart,
                    enter = expandVertically(
                        animationSpec = tween(300)
                    ),
                    exit = shrinkVertically(
                        animationSpec = tween(300)
                    )
                ) {
                    SwipeToDismiss(
                        state = dismissState,
                        background = {
                            if (dismissDirection != null) {
                                RedBackground(
                                    degrees = degrees,
                                    dismissDirection = dismissDirection
                                )
                            }
                        },
                        dismissThresholds = { FractionalThreshold(fraction = 0.33f) })
                    {
                        UserItem(
                            userName = item.toUser(),
                            navigateToDetailScreen = navigateToDetailScreen
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float, dismissDirection: DismissDirection) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(SMALL_PADDING)
            .clip(RoundedCornerShape(ROUNDED_CORNER_SIZE))
            .background(Color.Red.copy(0.8f))
            .padding(horizontal = LARGEST_PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (dismissDirection == DismissDirection.StartToEnd) {
            Icon(
                modifier = Modifier.rotate(degrees = degrees),
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.icon_delete),
                tint = MaterialTheme.colors.secondary
            )
        }
        if (dismissDirection == DismissDirection.EndToStart) {
            Spacer(modifier = Modifier.size(LARGEST_PADDING))
            Icon(
                modifier = Modifier.rotate(degrees = degrees),
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(id = R.string.icon_delete),
                tint = MaterialTheme.colors.secondary
            )
        }
    }
}