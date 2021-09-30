package dev.seno.jetgithub.ui.screens.favorite

import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarResult
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import dev.seno.jetgithub.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    navigateToDetailScreen: (String) -> Unit,
    navigateToMainScreen: () -> Unit,
    favoriteViewModel: FavoriteViewModel
) {

    val listFavorite by favoriteViewModel.favoriteListUser.collectAsState()
    val swipeDeleteMessage = LocalContext.current.getString(R.string.item_deleted)
    val actionLabel = LocalContext.current.getString(R.string.undo)
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            FavoriteAppBar(
                onBackClick = navigateToMainScreen,
                onDeleteAllClick = {
                    favoriteViewModel.deleteAllFavorite()
                }
            )
        }) {
        FavoriteContent(
            listFavorite = listFavorite,
            navigateToDetailScreen = navigateToDetailScreen,
            onSwipeToDelete = { favoriteUser ->
                LaunchedEffect(key1 = favoriteUser) {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    delay(300)
                    favoriteViewModel.deleteFavorite(favoriteUser.id)
                    scope.launch {
                        val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                            message = swipeDeleteMessage,
                            actionLabel = actionLabel
                        )
                        if (snackBarResult == SnackbarResult.ActionPerformed) {
                            favoriteViewModel.insertFavorite(favoriteUser)
                        }
                    }
                }
            }
        )
    }
}