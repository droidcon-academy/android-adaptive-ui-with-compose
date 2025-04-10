package com.droidcon.adaptiveinbox.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailLabels
import com.droidcon.adaptiveinbox.model.mailList
import com.droidcon.adaptiveinbox.utils.toDate
import com.droidcon.adaptiveinbox.utils.toFormattedString

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MailList(
    modifier: Modifier = Modifier,
    mailList: List<MailData>,
    selectedMailId: String?,
    onMailClicked: (mailId: String) -> Unit = {},
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SearchSupportedToolbar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp
                ),
            placeholder = stringResource(R.string.search_mails),
            onSearchQueryChanged = {
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.spacedBy(
                space = 12.dp
            ),
            contentPadding = PaddingValues(
                all = 16.dp
            )
        ) {
            items(mailList) { mailData ->
                MailListItem(
                    modifier = Modifier,
                    mailData = mailData,
                    onMailClicked = onMailClicked,
                    isOpened = mailData.mailId == selectedMailId,
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MailListItem(
    modifier: Modifier = Modifier,
    mailData: MailData,
    onMailClicked: (mailId: String) -> Unit,
    isOpened: Boolean,
) {
    BoxWithConstraints(
        modifier = modifier
    ) {
        val isCompact = this.maxWidth < 400.dp
        val isMedium = this.maxWidth in 400.dp..600.dp

        Card(
            modifier = Modifier
                .clip(CardDefaults.shape)
                .clickable {
                    onMailClicked(mailData.mailId)
                }
                .clip(CardDefaults.shape),
            colors = CardDefaults.cardColors(
                containerColor = if (isOpened) MaterialTheme.colorScheme.secondaryContainer
                else MaterialTheme.colorScheme.surfaceVariant
            ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                if (!isCompact) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        mailData.labels.forEach { label ->
                            MailLabelsListItem(
                                modifier = Modifier,
                                label = label
                            )
                        }
                    }
                }

                Spacer(
                    modifier = Modifier
                        .height(6.dp),
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfilePicture(
                        modifier = Modifier
                            .size(45.dp),
                        name = mailData.replies.first().senderDetails.fullName
                    )

                    Spacer(
                        modifier = Modifier
                            .width(6.dp),
                    )

                    Column(
                        modifier = Modifier
                            .wrapContentHeight()
                            .weight(1f)
                    ) {
                        Text(
                            text = mailData.replies.first().senderDetails.fullName,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Spacer(
                            modifier = Modifier
                                .height(4.dp),
                        )

                        Text(
                            text = mailData.subject,
                            style = MaterialTheme.typography.bodyLarge,
                        )

                        if (!isCompact) {
                            Text(
                                text = mailData.replies.last().body,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    if (!isCompact && !isMedium) {
                        Spacer(
                            modifier = Modifier
                                .width(6.dp),
                        )

                        Column(
                            modifier = Modifier,
                            verticalArrangement = Arrangement.spacedBy(
                                space = 8.dp
                            ),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = mailData.replies.last().timeMillis.toDate()
                                    .toFormattedString(),
                                style = MaterialTheme.typography.labelMedium,
                            )

                            IconButton(
                                onClick = {},
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                                    .size(18.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.StarBorder,
                                    contentDescription = "Favorite",
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MailLabelsListItem(
    modifier: Modifier = Modifier,
    label: MailLabels
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 6.dp, alignment = Alignment.CenterHorizontally
        )
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            painter = painterResource(label.labelIconRes),
            contentDescription = "Mail Label",
        )
        Text(
            text = label.labelName,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 8.sp
            ),
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview(widthDp = 300)
@Composable
private fun MailListItemPreview300W() {
    MailListItem(
        mailData = mailList.first(),
        onMailClicked = {},
        isOpened = true,
    )
}

@Preview(widthDp = 400)
@Composable
private fun MailListItemPreview400W() {
    MailListItem(
        mailData = mailList.first(),
        onMailClicked = {},
        isOpened = true,
    )
}

@Preview(widthDp = 700)
@Composable
private fun MailListItemPreview700W() {
    MailListItem(
        mailData = mailList.first(),
        onMailClicked = {},
        isOpened = true,
    )
}

@Preview(widthDp = 300)
@Composable
private fun MailListPreview300dp() {
    MailList(
        mailList = mailList,
        selectedMailId = null
    )
}

@Preview(widthDp = 400)
@Composable
private fun MailListPreview400dp() {
    MailList(
        mailList = mailList,
        selectedMailId = null
    )
}

@Preview(widthDp = 700)
@Composable
private fun MailListPreview700dp() {
    MailList(
        mailList = mailList,
        selectedMailId = null
    )
}