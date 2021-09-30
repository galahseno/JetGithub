package dev.seno.jetgithub.ui.screens.main

import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun MainScreen(
    navigateToDetail: (String) -> Unit,
    mainViewModel: MainViewModel,
    navigateToFavoriteScreen: () -> Unit
) {
    val listUser by mainViewModel.listUser.collectAsState()
    val themeState by mainViewModel.darkModeState.observeAsState()

    Scaffold(
        topBar = {
            MainAppBar(
                onSearchClick = {
                    mainViewModel.searchUser(it)
                },
                onCloseClick = {
                    mainViewModel.backToIdle()
                },
                navigateToFavoriteScreen = navigateToFavoriteScreen,
                onValueChange = {
                    mainViewModel.onValueChanged(it)
                },
                darkModeState = themeState
            )
        }) {
        MainContent(
            listUser = listUser,
            navigateToDetailScreen = navigateToDetail,
            themeState = themeState
        )
    }
}