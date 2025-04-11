package com.droidcon.adaptiveinbox.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droidcon.adaptiveinbox.model.MailAttachmentsNavRoute
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailRepliesData

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MailContainer(
    modifier: Modifier = Modifier,
    mailList: List<MailData>,
    selectedMail: MailData?,
    selectedMessageForAttachments: MailRepliesData?,
    onMailClicked: (mailId: String) -> Unit,
    onMailDetailsClosed: () -> Unit,
    onViewAttachmentsClicked: (mailData: MailRepliesData) -> Unit,
    onAttachmentsScreenClosed: () -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MailAttachmentsNavRoute.MailList
    ) {
        composable<MailAttachmentsNavRoute.MailList> {
            MailList(
                modifier = Modifier
                    .fillMaxSize(),
                mailList = mailList,
                selectedMailId = selectedMail?.mailId,
                onMailClicked = {
                    onMailClicked(it)
                    navController.navigate(MailAttachmentsNavRoute.Details)
                }
            )
        }
        composable<MailAttachmentsNavRoute.Details> {
            selectedMail?.let {
                MailDetails(
                    modifier = Modifier
                        .fillMaxSize(),
                    mailData = selectedMail,
                    showBackButton = true,
                    onViewAttachmentsClicked = {
                        onViewAttachmentsClicked(it)
                        navController.navigate(MailAttachmentsNavRoute.Attachments)
                    },
                    onBackPressed = {
                        onMailDetailsClosed()
                        navController.navigateUp()
                    }
                )
            }
        }
        composable<MailAttachmentsNavRoute.Attachments> {
            selectedMessageForAttachments?.let {
                MailAttachments(
                    modifier = Modifier
                        .fillMaxSize(),
                    attachments = selectedMessageForAttachments.attachments,
                    showBackButton = true,
                    onBackPressed = {
                        onAttachmentsScreenClosed()
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}