package com.droidcon.adaptiveinbox.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.model.AttachmentDetails
import com.droidcon.adaptiveinbox.model.mailList
import com.droidcon.adaptiveinbox.utils.kbToMbFormattedString
import com.droidcon.adaptiveinbox.utils.mimeTypeToFormattedString

@Composable
fun MailAttachments(
    modifier: Modifier = Modifier,
    attachments: List<AttachmentDetails>,
    showBackButton: Boolean,
    onBackPressed: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        BackSupportedToolbarWithText(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(R.string.view_attachments, attachments.size.toString()),
            hasBackButton = showBackButton,
            onBackPressed = onBackPressed,
        )

        Spacer(
            modifier = Modifier
                .height(16.dp)
        )

        MailAttachmentsList(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            attachments = attachments
        )
    }
}

@Composable
fun MailAttachmentsList(
    modifier: Modifier = Modifier,
    attachments: List<AttachmentDetails>
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(
            space = 12.dp, alignment = Alignment.Top
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(
            horizontal = 16.dp
        )
    ) {
        items(attachments) { attachment ->
            MailAttachmentsListItem(
                modifier = Modifier,
                attachment = attachment
            )
        }
    }
}

@Composable
fun MailAttachmentsListItem(
    modifier: Modifier = Modifier,
    attachment: AttachmentDetails
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
            val attachmentName = attachment.attachmentName
            val attachmentSize = attachment.attachmentSize.kbToMbFormattedString()
            val attachmentType = attachment.attachmentType.mimeTypeToFormattedString()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 6.dp
                )
            ) {
                Icon(
                    imageVector = Icons.Default.AttachFile,
                    contentDescription = "Attachment",
                    tint = MaterialTheme.colorScheme.outline
                )
                Text(
                    text = attachmentName,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier
                    .height(6.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(
                    space = 8.dp, alignment = Alignment.Start
                )
            ) {
                Text(
                    text = attachmentType,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = attachmentSize,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(
                modifier = Modifier
                    .height(8.dp)
            )

            OutlinedButton(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceBright
                )
            ) {
                Text(
                    text = stringResource(R.string.download),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Preview
@Composable
private fun MailAttachmentsListItemPreview() {
    MailAttachmentsListItem(
        modifier = Modifier,
        attachment = mailList.first().replies.first().attachments.first()
    )
}

@Preview
@Composable
private fun MailAttachmentsListPreview() {
    MailAttachmentsList(
        modifier = Modifier,
        attachments = mailList.first().replies.first().attachments
    )
}

@Preview
@Composable
private fun MailAttachmentsCompactPreview() {
    val mailRepliesData = mailList.first().replies.first()
    MailAttachments(
        modifier = Modifier,
        attachments = mailRepliesData.attachments + mailRepliesData.attachments,
        showBackButton = true,
        onBackPressed = {
        }
    )
}

@Preview
@Composable
private fun MailAttachmentsPreview() {
    val mailRepliesData = mailList.first().replies.first()
    MailAttachments(
        modifier = Modifier,
        attachments = mailRepliesData.attachments + mailRepliesData.attachments,
        showBackButton = true,
        onBackPressed = {
        }
    )
}
