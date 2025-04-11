package com.droidcon.adaptiveinbox.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.automirrored.outlined.Send
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.VideoChat
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Inbox
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.MailOutline
import androidx.compose.material.icons.outlined.VideoChat
import androidx.compose.ui.graphics.vector.ImageVector
import com.droidcon.adaptiveinbox.R
import kotlinx.serialization.Serializable

@Serializable
sealed interface BaseNavRoute

@Serializable
sealed interface MailDrawerNavRoute: BaseNavRoute {
    @Serializable
    data object Inbox : MailDrawerNavRoute

    @Serializable
    data object Sent : MailDrawerNavRoute

    @Serializable
    data object Spam : MailDrawerNavRoute

    @Serializable
    data object Trash : MailDrawerNavRoute
}

@Serializable
sealed interface MailMainNavRoute: BaseNavRoute {
    @Serializable
    data object MailBox: MailMainNavRoute

    @Serializable
    data object Meetings: MailMainNavRoute
}

@Serializable
sealed interface MailAttachmentsNavRoute {
    @Serializable
    data object MailList: MailAttachmentsNavRoute

    @Serializable
    data object Details: MailAttachmentsNavRoute

    @Serializable
    data object Attachments: MailAttachmentsNavRoute
}

data class MailDestination(
    val route: BaseNavRoute,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int
)

val HOME_MAIN_DESTINATIONS = listOf(
    MailDestination(
        route = MailMainNavRoute.MailBox,
        selectedIcon = Icons.Filled.Mail,
        unselectedIcon = Icons.Outlined.MailOutline,
        iconTextId = R.string.mail
    ),
    MailDestination(
        route = MailMainNavRoute.Meetings,
        selectedIcon = Icons.Filled.VideoChat,
        unselectedIcon = Icons.Outlined.VideoChat,
        iconTextId = R.string.meetings
    )
)

val HOME_DRAWER_DESTINATIONS = listOf(
    MailDestination(
        route = MailDrawerNavRoute.Inbox,
        selectedIcon = Icons.Filled.Inbox,
        unselectedIcon = Icons.Outlined.Inbox,
        iconTextId = R.string.inbox
    ),
    MailDestination(
        route = MailDrawerNavRoute.Sent,
        selectedIcon = Icons.AutoMirrored.Filled.Send,
        unselectedIcon = Icons.AutoMirrored.Outlined.Send,
        iconTextId = R.string.sent
    ),
    MailDestination(
        route = MailDrawerNavRoute.Spam,
        selectedIcon = Icons.Filled.Info,
        unselectedIcon = Icons.Outlined.Info,
        iconTextId = R.string.spam
    ),
    MailDestination(
        route = MailDrawerNavRoute.Trash,
        selectedIcon = Icons.Filled.Delete,
        unselectedIcon = Icons.Outlined.Delete,
        iconTextId = R.string.trash
    )
)
