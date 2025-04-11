package com.droidcon.adaptiveinbox.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.droidcon.adaptiveinbox.model.MailDrawerNavRoute
import com.droidcon.adaptiveinbox.model.MailMainNavRoute
import com.droidcon.adaptiveinbox.model.MailType
import com.droidcon.adaptiveinbox.utils.MailNavigationActions
import com.droidcon.adaptiveinbox.viewmodel.MailViewModel

@Composable
fun AdaptiveMailNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = MailMainNavRoute.MailBox,
    ) {
        navigation<MailMainNavRoute.MailBox>(
            startDestination = MailDrawerNavRoute.Inbox,
        ) {
            @Composable
            fun MailUI(
                mailType: MailType
            ) {
                val viewModel: MailViewModel = hiltViewModel()
                val mailUIState by viewModel.mailUiState.collectAsState()

                LaunchedEffect(mailType) {
                    viewModel.getMails(mailType = mailType)
                }

                MailContainer(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding(),
                    mailList = mailUIState.mailList,
                    selectedMail = mailUIState.selectedMail,
                    selectedMessageForAttachments = mailUIState.selectedMessageForAttachments,
                    onMailClicked = { mailId ->
                        viewModel.openMailDetailsScreen(mailId)
                    },
                    onMailDetailsClosed = {
                        viewModel.closeMailDetailsScreen()
                    },
                    onViewAttachmentsClicked = {
                        viewModel.openAttachmentDetailsScreen(
                            selectedMessageForAttachments = it
                        )
                    },
                    onAttachmentsScreenClosed = {
                        viewModel.closeAttachmentDetailsScreen()
                    },
                )
            }

            composable<MailDrawerNavRoute.Inbox> {
                MailUI(
                    MailType.INBOX
                )
            }

            composable<MailDrawerNavRoute.Sent> {
                MailUI(
                    MailType.SENT
                )
            }

            composable<MailDrawerNavRoute.Spam> {
                MailUI(
                    MailType.SPAM
                )
            }

            composable<MailDrawerNavRoute.Trash> {
                MailUI(
                    MailType.TRASH
                )
            }
        }
        composable<MailMainNavRoute.Meetings> {
            val viewModel: MailViewModel = hiltViewModel()
            val meetingsUiState by viewModel.meetingsUiState.collectAsState()

            LaunchedEffect(true) {
                viewModel.getMeetingsData()
            }

            MeetingsUI(
                modifier = Modifier
                    .fillMaxSize()
                    .safeDrawingPadding(),
                carousel = meetingsUiState.carousel,
                meetings = meetingsUiState.meetings,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun AdaptiveInboxNavigator(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val navigationActions = remember(navController) {
        MailNavigationActions(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentNavBarDestination = navBackStackEntry?.destination

    AdaptiveInboxNavigator(
        modifier = modifier,
        currentDestination = currentNavBarDestination,
        onNavigate = { destination ->
            navigationActions.navigateTo(destination)
        }
    ) {
        AdaptiveMailNavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
        )
    }
}