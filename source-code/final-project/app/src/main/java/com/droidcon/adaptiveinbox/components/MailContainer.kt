package com.droidcon.adaptiveinbox.components

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.layout.PaneExpansionAnchor
import androidx.compose.material3.adaptive.layout.ThreePaneScaffoldDestinationItem
import androidx.compose.material3.adaptive.layout.defaultDragHandleSemantics
import androidx.compose.material3.adaptive.layout.rememberPaneExpansionState
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.window.layout.DisplayFeature
import com.droidcon.adaptiveinbox.model.MailData
import com.droidcon.adaptiveinbox.model.MailRepliesData
import com.droidcon.adaptiveinbox.model.PaneType
import com.droidcon.adaptiveinbox.utils.isDetailPaneVisible
import com.droidcon.adaptiveinbox.utils.isExtraPaneVisible
import com.droidcon.adaptiveinbox.utils.isListPaneVisible
import com.google.accompanist.adaptive.TwoPane
import com.google.accompanist.adaptive.VerticalTwoPaneStrategy
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MailContainer(
    modifier: Modifier = Modifier,
    mailList: List<MailData>,
    selectedMail: MailData?,
    selectedMessageForAttachments: MailRepliesData?,
    paneType: PaneType,
    displayFeatures: List<DisplayFeature>,
    onMailClicked: (mailId: String) -> Unit,
    onMailDetailsClosed: () -> Unit,
    onViewAttachmentsClicked: (mailData: MailRepliesData) -> Unit,
    onAttachmentsScreenClosed: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    when (paneType) {
        PaneType.DUAL_PANE -> {
            val attachmentSheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = false
            )
            var showAttachmentBottomSheet by remember { mutableStateOf(false) }

            if (showAttachmentBottomSheet) {
                ModalBottomSheet(
                    modifier = Modifier
                        .wrapContentSize(),
                    sheetState = attachmentSheetState,
                    onDismissRequest = {
                        onAttachmentsScreenClosed()
                        showAttachmentBottomSheet = false
                        coroutineScope.launch {
                            attachmentSheetState.hide()
                        }
                    }
                ) {
                    selectedMessageForAttachments?.let {
                        MailAttachments(
                            modifier = Modifier
                                .fillMaxSize(),
                            attachments = selectedMessageForAttachments.attachments,
                            showBackButton = false,
                            onBackPressed = {
                                onAttachmentsScreenClosed()
                            }
                        )
                    }
                }
            }

            if (selectedMessageForAttachments != null) {
                showAttachmentBottomSheet = true
            }

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
                    selectedMail?.let {
                        MailDetails(
                            modifier = Modifier
                                .fillMaxSize(),
                            mailData = selectedMail,
                            showBackButton = false,
                            onViewAttachmentsClicked = onViewAttachmentsClicked,
                            onBackPressed = {
                                onMailDetailsClosed()
                            }
                        )
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
            val mutableInteractionSource = remember { MutableInteractionSource() }
            val paneExpansionState = rememberPaneExpansionState(
                anchors = listOf(
                    PaneExpansionAnchor.Proportion(0f),
                    PaneExpansionAnchor.Proportion(0.25f),
                    PaneExpansionAnchor.Proportion(0.5f),
                    PaneExpansionAnchor.Proportion(0.75f),
                    PaneExpansionAnchor.Proportion(1f),
                ),
            )

            val navigator = rememberListDetailPaneScaffoldNavigator(
                initialDestinationHistory = listOfNotNull(
                    ThreePaneScaffoldDestinationItem<ListDetailPaneScaffoldRole>(
                        pane = ListDetailPaneScaffoldRole.List
                    ),
                    ThreePaneScaffoldDestinationItem<ListDetailPaneScaffoldRole>(
                        pane = ListDetailPaneScaffoldRole.Detail
                    ).takeIf {
                        selectedMail != null
                    }
                )
            )

            val listPaneVisible = navigator.isListPaneVisible()
            val detailPaneVisible = navigator.isDetailPaneVisible()
            val extraPaneVisible = navigator.isExtraPaneVisible()

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
                            onMailClicked = {
                                onMailClicked(it)
                                coroutineScope.launch {
                                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                }
                            },
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
                                showBackButton = !listPaneVisible && detailPaneVisible && !extraPaneVisible,
                                onViewAttachmentsClicked = {
                                    onViewAttachmentsClicked(it)
                                    coroutineScope.launch {
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                        navigator.navigateTo(ListDetailPaneScaffoldRole.Extra)
                                    }
                                },
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
                                showBackButton = !listPaneVisible && !detailPaneVisible && extraPaneVisible,
                                onBackPressed = {
                                    onAttachmentsScreenClosed()
                                    coroutineScope.launch {
                                        navigator.navigateBack()
                                    }
                                }
                            )
                        }
                    }
                },
                paneExpansionState = paneExpansionState,
                paneExpansionDragHandle = {
                    VerticalDragHandle(
                        modifier = Modifier
                            .height(80.dp)
                            .width(4.dp)
                            .paneExpansionDraggable(
                            state = paneExpansionState,
                            minTouchTargetSize = LocalMinimumInteractiveComponentSize.current,
                            interactionSource = mutableInteractionSource,
                            semanticsProperties = paneExpansionState.defaultDragHandleSemantics(),
                        ),
                    )
                }
            )
        }
    }
}