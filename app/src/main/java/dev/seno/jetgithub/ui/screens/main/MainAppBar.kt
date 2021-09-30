package dev.seno.jetgithub.ui.screens.main

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import dev.seno.jetgithub.R
import dev.seno.jetgithub.ui.component.dialog.SettingAlertDialog
import dev.seno.jetgithub.ui.theme.TOP_APP_BAR_HEIGHT

@Composable
fun MainAppBar(
    onSearchClick: (String) -> Unit,
    onCloseClick: () -> Unit,
    navigateToFavoriteScreen: () -> Unit,
    onValueChange: (Boolean) -> Unit,
    darkModeState: Boolean?
) {
    var searchAppBarState by remember { mutableStateOf(false) }
    var openSettingDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    SettingAlertDialog(
        title = stringResource(id = R.string.dark_mode),
        openDialog = openSettingDialog,
        closeDialog = { openSettingDialog = false },
        onValueChange = {
            onValueChange(it)
        },
        value = darkModeState
    )

    if (!searchAppBarState) {
        DefaultAppBar(
            onSearchClick = { searchAppBarState = true },
            onFavoriteClick = { navigateToFavoriteScreen() },
            onSettingClick = { openSettingDialog = true }
        )
    } else {
        SearchAppBar(
            text = searchText,
            onTextChange = { newText ->
                searchText = newText
            },
            onCloseClick = {
                searchAppBarState = false
                onCloseClick()
            },
            onSearchClick = {
                if (it.isNotEmpty()) onSearchClick(it)
            }
        )
    }
}

@Composable
fun DefaultAppBar(
    onSearchClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    onSettingClick: () -> Unit,
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                color = MaterialTheme.colors.secondary
            )
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.icon_search),
                    tint = MaterialTheme.colors.secondary
                )
            }
            IconButton(onClick = { onFavoriteClick() }) {
                Icon(
                    imageVector = Icons.Filled.FavoriteBorder,
                    contentDescription = stringResource(id = R.string.icon_favorite),
                    tint = MaterialTheme.colors.secondary
                )
            }
            IconButton(onClick = { onSettingClick() }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = stringResource(id = R.string.icon_setting),
                    tint = MaterialTheme.colors.secondary
                )
            }
        },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClick: () -> Unit,
    onSearchClick: (String) -> Unit
) {
    val inputService = LocalTextInputService.current
    val focusRequest = FocusRequester()

    SideEffect {
        focusRequest.requestFocus()
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = MaterialTheme.colors.primary
    ) {
        TextField(
            modifier = Modifier.focusRequester(focusRequest),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = stringResource(id = R.string.search_placeholder),
                    color = MaterialTheme.colors.secondary
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colors.secondary,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            ),
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier.alpha(ContentAlpha.disabled),
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(id = R.string.icon_search),
                    tint = MaterialTheme.colors.secondary
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    if (text.isEmpty()) onCloseClick() else {
                        inputService?.showSoftwareKeyboard()
                        onTextChange("")
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(id = R.string.icon_close),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClick(text)
                    inputService?.hideSoftwareKeyboard()
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                cursorColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                backgroundColor = Color.Transparent
            )
        )
    }
}

@Preview()
@Composable
fun PreviewDefaultAppBar() {
    DefaultAppBar(onSearchClick = {}, onFavoriteClick = {}, onSettingClick = {})
}

@Preview
@Composable
fun PreviewSearchAppBar() {
    SearchAppBar(
        text = "Search",
        onTextChange = {},
        onCloseClick = { /*TODO*/ },
        onSearchClick = {}
    )
}