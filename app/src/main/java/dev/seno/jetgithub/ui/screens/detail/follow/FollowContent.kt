package dev.seno.jetgithub.ui.screens.detail.follow

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import dev.seno.jetgithub.data.model.User
import dev.seno.jetgithub.ui.component.DisplayEmptyContentOrError
import dev.seno.jetgithub.ui.component.PlaceholderUserItem
import dev.seno.jetgithub.ui.component.UserItem
import dev.seno.jetgithub.ui.theme.SMALL_PADDING
import dev.seno.jetgithub.utils.DisplayState
import dev.seno.jetgithub.utils.Resource

@Composable
fun FollowContent(
    listUser: Resource<List<User>>,
    navigateToDetailScreen: (String) -> Unit,
    onScrollListener: (Int) -> Unit
) {
    when (listUser) {
        is Resource.Loading -> PlaceholderListFollow()
        is Resource.Success -> UserFollowItem(
            listUser = listUser.data,
            navigateToDetailScreen = navigateToDetailScreen,
            onScrollListener = onScrollListener
        )
        else -> DisplayEmptyContentOrError(DisplayState.Error)
    }
}

@Composable
fun PlaceholderListFollow() {
    LazyColumn(
        modifier = Modifier.padding(SMALL_PADDING)
    ) {
        items(count = 5) {
            PlaceholderUserItem()
        }
    }
}

@Composable
fun UserFollowItem(
    listUser: List<User>,
    navigateToDetailScreen: (String) -> Unit,
    onScrollListener: (Int) -> Unit
) {
    if (listUser.isEmpty()) {
        DisplayEmptyContentOrError(DisplayState.Follow)
    } else {
        val listState = rememberLazyListState()
        val visibleIndex = listState.firstVisibleItemIndex

        LaunchedEffect(key1 = visibleIndex) {
            onScrollListener(visibleIndex)
        }

        LazyColumn(
            state = listState,
            modifier = Modifier
                .padding(SMALL_PADDING)
                .fillMaxSize()
        ) {
            items(
                items = listUser,
                key = {
                    it.username
                }
            ) { item ->
                UserItem(userName = item, navigateToDetailScreen = navigateToDetailScreen)
            }
        }
    }
}