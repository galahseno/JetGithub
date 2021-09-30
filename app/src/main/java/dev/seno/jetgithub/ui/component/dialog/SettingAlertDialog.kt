package dev.seno.jetgithub.ui.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import dev.seno.jetgithub.R
import dev.seno.jetgithub.ui.theme.LARGE_PADDING

@Composable
fun SettingAlertDialog(
    title: String,
    openDialog: Boolean,
    closeDialog: () -> Unit,
    value: Boolean?,
    onValueChange: (Boolean) -> Unit,
) {
    if (openDialog) {
        AlertDialog(
            backgroundColor = MaterialTheme.colors.primary,
            title = {
                Text(
                    text = title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.secondary,
                )
            },
            onDismissRequest = { closeDialog() },
            buttons = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LARGE_PADDING),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.dark_mode_text),
                        color = MaterialTheme.colors.secondary,
                    )
                    if (value != null) {
                        Switch(
                            checked = value,
                            onCheckedChange = {
                                onValueChange(it)
                            }
                        )
                    }
                }
            }
        )
    }
}