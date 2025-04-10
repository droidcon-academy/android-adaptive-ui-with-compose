package com.droidcon.adaptiveinbox.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import com.droidcon.adaptiveinbox.model.MailAttachmentsNavRoute
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailRepliesData
import com.droidcon.adaptiveinbox.model.PaneType
import com.droidcon.adaptiveinbox.utils.isDetailPaneVisible
import com.droidcon.adaptiveinbox.utils.isExtraPaneVisible
import com.droidcon.adaptiveinbox.utils.isListPaneVisible
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun MailContainer(
    modifier: Modifier = Modifier,
    mailList: List<MailData>,
    selectedMail: MailData?,
    isMailDetailsScreenOpened: Boolean,
    isAttachmentsListOpened: Boolean,
    selectedMessageForAttachments: MailRepliesData?,
    paneType: PaneType,
    displayFeatures: List<DisplayFeature>,
    isCompact: Boolean,
    onMailClicked: (mailId: String) -> Unit,
    onMailDetailsClosed: () -> Unit,
    onViewAttachmentsClicked: (mailData: MailRepliesData) -> Unit,
    onAttachmentsScreenClosed: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    when (paneType) {
        PaneType.DUAL_PANE -> {
            TwoPane(
                modifier = modifier,
                first = {
                    MailList(
                        modifier = Modifier
                            .fillMaxSize(),
                        mailList = mailList,
                        selectedMailId = selectedMail?.mailId,
                        onMailClicked = onMailClicked
                    )
                },
                second = {
                    val navController = rememberNavController()

                    LaunchedEffect(selectedMessageForAttachments) {
                        if (selectedMessageForAttachments != null) {
                            navController.navigate(MailAttachmentsNavRoute.Attachments)
                        }
                    }

                    NavHost(
                        navController = navController,
                        startDestination = MailAttachmentsNavRoute.Details,
                    ) {
                        composable<MailAttachmentsNavRoute.Details> {
                            selectedMail?.let {
                                MailDetails(
                                    modifier = Modifier
                                        .fillMaxSize(),
                                    mailData = selectedMail,
                                    showBackButton = isMailDetailsScreenOpened,
                                    onViewAttachmentsClicked = onViewAttachmentsClicked,
                                    onBackPressed = {
                                        onMailDetailsClosed()
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
                                    onBackPressed = {
                                        onAttachmentsScreenClosed()
                                        coroutineScope.launch {
                                            navController.navigateUp()
                                        }
                                    }
                                )
                            }
                        }
                    }
                },
                strategy = VerticalTwoPaneStrategy(
                    splitFraction = 0.5f
                ),
                displayFeatures = displayFeatures
            )
        }

        PaneType.ADAPTIVE_PANE,
        PaneType.SINGLE_PANE -> {
            val navigator = rememberListDetailPaneScaffoldNavigator()

            LaunchedEffect(
                navigator.isListPaneVisible(),
                navigator.isDetailPaneVisible(),
                navigator.isExtraPaneVisible()
            ) {
                if (!navigator.isDetailPaneVisible() && !navigator.isExtraPaneVisible() && isMailDetailsScreenOpened) {
                    onMailDetailsClosed()
                }
                if (!navigator.isExtraPaneVisible()) {
                    onAttachmentsScreenClosed()
                }
            }

            LaunchedEffect(
                selectedMail,
                selectedMessageForAttachments,
            ) {
                if (selectedMail == null) {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.List)
                }
                if (selectedMail != null) {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                }
                if (selectedMessageForAttachments != null) {
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
                }
            }

            NavigableListDetailPaneScaffold(
                modifier = modifier,
                navigator = navigator,
                listPane = {
                    AnimatedPane(
                        enterTransition = slideInHorizontally() + fadeIn(),
                        exitTransition = slideOutHorizontally() + fadeOut()
                    ) {
                        MailList(
                            modifier = Modifier
                                .fillMaxSize(),
                            mailList = mailList,
                            onMailClicked = onMailClicked,
                            selectedMailId = selectedMail?.mailId
                        )
                    }
                },
                detailPane = {
                    AnimatedPane(
                        enterTransition = slideInHorizontally() + fadeIn(),
                        exitTransition = slideOutHorizontally() + fadeOut()
                    ) {
                        selectedMail?.let {
                            MailDetails(
                                modifier = Modifier
                                    .fillMaxSize(),
                                mailData = selectedMail,
                                showBackButton = isMailDetailsScreenOpened,
                                onViewAttachmentsClicked = onViewAttachmentsClicked,
                                onBackPressed = {
                                    onMailDetailsClosed()
                                    coroutineScope.launch {
                                        navigator.navigateBack()
                                    }
                                }
                            )
                        }
                    }
                },
                extraPane = {
                    selectedMessageForAttachments?.let {
                        AnimatedPane(
                            enterTransition = slideInHorizontally() + fadeIn(),
                            exitTransition = slideOutHorizontally() + fadeOut()
                        ) {
                            MailAttachments(
                                modifier = Modifier
                                    .fillMaxSize(),
                                attachments = selectedMessageForAttachments.attachments,
                                onBackPressed = {
                                    onAttachmentsScreenClosed()
                                    coroutineScope.launch {
                                        navigator.navigateBack()
                                    }
                                }
                            )
                        }
                    }
                }
            )
        }
    }
}