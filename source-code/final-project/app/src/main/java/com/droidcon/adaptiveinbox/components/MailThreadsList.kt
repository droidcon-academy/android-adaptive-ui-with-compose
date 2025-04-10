package com.droidcon.adaptiveinbox.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Attachment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.model.MailRepliesData
import com.droidcon.adaptiveinbox.model.mailList
import com.droidcon.adaptiveinbox.utils.toDate
import com.droidcon.adaptiveinbox.utils.toFormattedString

@Composable
fun MailThreadsList(
    modifier: Modifier = Modifier,
    mailThreads: List<MailRepliesData>,
    onViewAttachmentsClicked: (mailData: MailRepliesData) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp
        ),
        contentPadding = PaddingValues(
            all = 16.dp
        )
    ) {
        items(mailThreads) { mailData ->
            MailThreadItem(
                modifier = Modifier,
                mailData = mailData,
                onViewAttachmentsClicked = onViewAttachmentsClicked
            )
        }
    }
}

@Composable
fun MailThreadItem(
    modifier: Modifier = Modifier,
    mailData: MailRepliesData,
    onViewAttachmentsClicked: (mailData: MailRepliesData) -> Unit,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                ProfilePicture(
                    modifier = Modifier
                        .size(45.dp),
                    name = mailData.senderDetails.fullName
                )
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = mailData.senderDetails.fullName,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = mailData.timeMillis.toDate().toFormattedString(),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }

            Text(
                text = mailData.body,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            if (mailData.attachments.isNotEmpty()) {
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )

                Row(
                    modifier = Modifier
                        .clickable {
                            onViewAttachmentsClicked(mailData)
                        },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(22.dp),
                        imageVector = Icons.Default.Attachment,
                        contentDescription = "Attachment"
                    )
                    Text(
                        text = stringResource(R.string.view_attachments),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun MailThreadItemPreview() {
    MailThreadItem(
        modifier = Modifier,
        mailData = mailList.first().replies.first(),
        onViewAttachmentsClicked = {
        }
    )
}

@Preview
@Composable
private fun MailThreadListPreview() {
    MailThreadsList(
        modifier = Modifier
            .fillMaxSize(),
        mailThreads = mailList.first().replies,
        onViewAttachmentsClicked = {
        }
    )
}