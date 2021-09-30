package dev.seno.jetgithub.ui.screens.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import dev.seno.jetgithub.R

@Composable
fun DetailAppBar(
    username: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = username,
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
        backgroundColor = MaterialTheme.colors.primary
    )
}

@Preview
@Composable
fun PreviewDetailAppBar() {
    DetailAppBar(username = "Detail", onBackClick = {})
}