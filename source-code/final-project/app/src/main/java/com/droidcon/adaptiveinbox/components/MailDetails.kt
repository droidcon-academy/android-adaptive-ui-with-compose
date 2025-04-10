package com.droidcon.adaptiveinbox.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailRepliesData
import com.droidcon.adaptiveinbox.model.mailList

@Composable
fun MailDetails(
    modifier: Modifier = Modifier,
    mailData: MailData,
    showBackButton: Boolean,
    onViewAttachmentsClicked: (mailData: MailRepliesData) -> Unit,
    onBackPressed: () -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        Column(
            modifier = modifier
                .weight(1f)
        ) {
            BackSupportedToolbarWithText(
                modifier = Modifier
                    .fillMaxWidth(),
                title = mailData.subject,
                subtitle = stringResource(
                    R.string.replies_with_count,
                    mailData.replies.size.toString()
                ),
                hasBackButton = showBackButton,
                onBackPressed = onBackPressed,
            )

            MailThreadsList(
                modifier = Modifier
                    .fillMaxSize(),
                mailThreads = mailData.replies,
                onViewAttachmentsClicked = onViewAttachmentsClicked
            )
        }

        MailActioningStrip(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp,
                    horizontal = 16.dp
                ),
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MailActioningStrip(
    modifier: Modifier = Modifier,
) {
    BoxWithConstraints { 
        val itemsPerRow = if (this.maxWidth > 600.dp) {
            3
        } else {
            2
        }
        FlowRow(
            modifier = modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            maxItemsInEachRow = itemsPerRow
        ) {
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright
                )
            ) {
                Text(
                    text = stringResource(R.string.forward),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright
                )
            ) {
                Text(
                    text = stringResource(R.string.reply),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright
                )
            ) {
                Text(
                    text = stringResource(R.string.reply_all),
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun MailActioningStripPreview() {
    MailActioningStrip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 20.dp,
                bottom = 8.dp,
                start = 16.dp,
                end = 16.dp
            )
    )
}

@Preview
@Composable
private fun MailDetailsPreview() {
    MailDetails(
        modifier = Modifier
            .fillMaxSize(),
        mailData = mailList.first(),
        showBackButton = true,
        onViewAttachmentsClicked = {
        },
        onBackPressed = {
        }
    )
}