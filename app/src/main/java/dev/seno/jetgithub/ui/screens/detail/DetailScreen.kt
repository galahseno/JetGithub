package dev.seno.jetgithub.ui.screens.detail

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.offset
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import dev.seno.jetgithub.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(
    username: String,
    navigateToMainScreen: () -> Unit,
    detailViewModel: DetailViewModel,
    navigateToDetailScreen: (String) -> Unit
) {

    val detailUser by detailViewModel.detailUser.collectAsState()

    var offset by remember { mutableStateOf(0.dp) }
    val offsetAnimate by animateDpAsState(
        targetValue = offset,
        animationSpec = tween(1000)
    )
    var favorite by remember {
        mutableStateOf(detailViewModel.checkFavoriteState(username))
    }

    LaunchedEffect(key1 = username) {
        delay(1000)
        detailViewModel.apply {
            getFollowing(username)
            getFollower(username)
        }
    }

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val addFavorite = LocalContext.current.getString(R.string.add_favorite)
    val removeFavorite = LocalContext.current.getString(R.string.remove_favorite)
    val actionLabel = LocalContext.current.getString(R.string.ok)

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DetailAppBar(
                username = username,
                onBackClick = navigateToMainScreen
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                    favorite = !favorite
                    detailViewModel.handleInsertOrDeleteFavorite(favorite)
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = if (favorite) addFavorite else removeFavorite,
                            actionLabel = actionLabel
                        )
                    }
                },
                modifier = Modifier
                    .offset(y = offsetAnimate),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.secondary
            ) {
                Icon(
                    imageVector = if (favorite) Icons.Filled.Favorite
                    else Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.icon_favorite)
                )
            }
        }
    ) {
        DetailContent(
            detailUser = detailUser,
            navigateToDetailScreen = navigateToDetailScreen,
            onScrollListener = {
                offset = if (it < 2) 0.dp else 150.dp
            }
        )
    }
}