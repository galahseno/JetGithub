package dev.seno.jetgithub.ui.screens.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.seno.jetgithub.R
import dev.seno.jetgithub.ui.component.dialog.DisplayAlertDialog

@Composable
fun FavoriteAppBar(
    onBackClick: () -> Unit,
    onDeleteAllClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.favorite),
                color = MaterialTheme.colors.secondary
            )
        },
        navigationIcon = {
            Box(contentAlignment = Alignment.Center) {
                IconButton(onClick = { onBackClick() }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBackIosNew,
                        contentDescription = stringResource(id = R.string.icon_back),
                        tint = MaterialTheme.colors.secondary
                    )
                }
            }
        },
        actions = { DeleteAction { onDeleteAllClick() } },
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Composable
fun DeleteAction(
    onDeleteAllClick: () -> Unit
) {
    var openDialog by remember { mutableStateOf(false) }

    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_all_title),
        message = stringResource(id = R.string.delete_all_message),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteAllClick() }
    )

    IconButton(onClick = { openDialog = true }) {
        Icon(
            imageVector = Icons.Filled.DeleteOutline,
            contentDescription = stringResource(id = R.string.icon_delete),
            tint = MaterialTheme.colors.secondary
        )
    }
}

@Preview
@Composable
fun PreviewFavoriteAppBar() {
    FavoriteAppBar(onDeleteAllClick = {}, onBackClick = {})
}