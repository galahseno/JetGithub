package dev.seno.jetgithub.ui.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.seno.jetgithub.data.model.ListUser
import dev.seno.jetgithub.ui.component.DisplayEmptyContentOrError
import dev.seno.jetgithub.ui.component.IdleState
import dev.seno.jetgithub.ui.component.PlaceholderUserItem
import dev.seno.jetgithub.ui.component.UserItem
import dev.seno.jetgithub.ui.theme.SMALL_PADDING
import dev.seno.jetgithub.utils.DisplayState
import dev.seno.jetgithub.utils.Resource

@Composable
fun MainContent(
    listUser: Resource<ListUser>,
    navigateToDetailScreen: (String) -> Unit,
    themeState: Boolean?
) {
    when (listUser) {
        is Resource.Idle -> IdleState(themeState)
        is Resource.Loading -> PlaceholderUserList()
        is Resource.Success -> UserListItem(
            listUser = listUser.data,
            navigateToDetailScreen = navigateToDetailScreen
        )
        is Resource.Error -> DisplayEmptyContentOrError(DisplayState.Error)
    }
}

@Composable
fun PlaceholderUserList() {
    LazyColumn(
        modifier = Modifier.padding(SMALL_PADDING)
    ) {
        items(count = 10) {
            PlaceholderUserItem()
        }
    }
}

@Composable
fun UserListItem(
    listUser: ListUser,
    navigateToDetailScreen: (String) -> Unit
) {
    if (listUser.listUser.isEmpty()) {
        DisplayEmptyContentOrError(DisplayState.Search)
    } else {
        LazyColumn(
            modifier = Modifier.padding(SMALL_PADDING)
        ) {
            items(
                items = listUser.listUser,
                key = {
                    it.username
                }
            ) { item ->
                UserItem(userName = item, navigateToDetailScreen = navigateToDetailScreen)
            }
        }
    }
}