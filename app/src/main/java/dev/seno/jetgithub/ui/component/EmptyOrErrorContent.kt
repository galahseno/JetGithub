package dev.seno.jetgithub.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.PersonSearch
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.seno.jetgithub.R
import dev.seno.jetgithub.ui.theme.LARGE_PADDING
import dev.seno.jetgithub.ui.theme.MediumGray
import dev.seno.jetgithub.utils.DisplayState

@Composable
fun DisplayEmptyContentOrError(displayState: DisplayState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(120.dp),
            imageVector = when (displayState) {
                DisplayState.Error -> Icons.Filled.Error
                DisplayState.Follow -> Icons.Filled.PersonSearch
                else -> Icons.Filled.SearchOff
            },
            contentDescription = when (displayState) {
                DisplayState.Error -> stringResource(id = R.string.icon_error)
                else -> stringResource(id = R.string.icon_empty)
            },
            tint = MediumGray
        )
        Spacer(modifier = Modifier.height(LARGE_PADDING))
        Text(
            text = when (displayState) {
                DisplayState.Error -> stringResource(id = R.string.error_user)
                DisplayState.Favorite -> stringResource(id = R.string.no_favorite)
                else -> stringResource(id = R.string.empty_user)
            },
            color = MediumGray,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h6.fontSize
        )
    }
}

@Preview
@Composable
fun PreviewDisplayEmptyContentOrError() {
    DisplayEmptyContentOrError(DisplayState.Favorite)
}