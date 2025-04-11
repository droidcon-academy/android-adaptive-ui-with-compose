package com.droidcon.adaptiveinbox.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuOpen
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldLayout
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import com.droidcon.adaptiveinbox.R
import com.droidcon.adaptiveinbox.model.HOME_DRAWER_DESTINATIONS
import com.droidcon.adaptiveinbox.model.HOME_MAIN_DESTINATIONS
import com.droidcon.adaptiveinbox.model.MailDestination
import com.droidcon.adaptiveinbox.utils.hasRoute
import kotlinx.coroutines.launch

@Composable
fun AdaptiveInboxNavigator(
    modifier: Modifier = Modifier,
    currentDestination: NavDestination?,
    navLayoutType: NavigationSuiteType,
    onNavigate: (MailDestination) -> Unit,
    content: @Composable () -> Unit
) {
    var drawerState =
        rememberDrawerState(initialValue = DrawerValue.Closed)

    val coroutineScope = rememberCoroutineScope()

    BackHandler(enabled = drawerState.isOpen) {
        coroutineScope.launch {
            drawerState.close()
        }
    }

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            MailMainNavigationDrawer(
                currentDestination = currentDestination,
                navigateToTopLevelDestination = { destination ->
                    onNavigate(destination)
                    coroutineScope.launch {
                        drawerState.close()
                    }
                },
                onDrawerClicked = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                }
            )
        }
    ) {
        NavigationSuiteScaffoldLayout(
            layoutType = navLayoutType,
            navigationSuite = {
                when (navLayoutType) {
                    NavigationSuiteType.NavigationBar -> MainBottomNavigationBar(
                        currentDestination = currentDestination,
                        navigateToTopLevelDestination = onNavigate
                    )

                    NavigationSuiteType.NavigationRail -> MainNavigationRail(
                        currentDestination = currentDestination,
                        navigateToTopLevelDestination = onNavigate,
                        onDrawerClicked = {
                            coroutineScope.launch {
                                drawerState.open()
                            }
                        }
                    )
                }
            }
        ) {
            content()
        }
    }
}


@Composable
fun MainNavigationRail(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (MailDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 4.dp
            )
        ) {
            NavigationRailItem(
                selected = false,
                onClick = onDrawerClicked,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Navigation Drawer"
                    )
                }
            )
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(top = 8.dp, bottom = 32.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Compose",
                    modifier = Modifier.size(18.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = 4.dp
            )
        ) {
            HOME_MAIN_DESTINATIONS.forEach { replyDestination ->
                val isSelected = currentDestination.hasRoute(replyDestination)
                        || currentDestination?.parent.hasRoute(replyDestination)

                NavigationRailItem(
                    selected = isSelected,
                    onClick = { navigateToTopLevelDestination(replyDestination) },
                    icon = {
                        Icon(
                            imageVector = if (isSelected)
                                replyDestination.selectedIcon
                            else
                                replyDestination.unselectedIcon,
                            contentDescription = stringResource(
                                id = replyDestination.iconTextId
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun MainBottomNavigationBar(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (MailDestination) -> Unit
) {
    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        HOME_MAIN_DESTINATIONS.forEach { replyDestination ->
            val isSelected = currentDestination.hasRoute(replyDestination)
                    || currentDestination?.parent.hasRoute(replyDestination)
            NavigationBarItem(
                label = {
                    Text(
                        text = stringResource(id = replyDestination.iconTextId),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                selected = isSelected,
                onClick = { navigateToTopLevelDestination(replyDestination) },
                icon = {
                    Icon(
                        imageVector = if (isSelected)
                            replyDestination.selectedIcon
                        else
                            replyDestination.unselectedIcon,
                        contentDescription = stringResource(id = replyDestination.iconTextId)
                    )
                }
            )
        }
    }
}

@Composable
fun MailMainNavigationDrawer(
    currentDestination: NavDestination?,
    navigateToTopLevelDestination: (MailDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    ModalDrawerSheet {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.app_name).uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
            IconButton(onClick = onDrawerClicked) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.MenuOpen,
                    contentDescription = "Close Drawer"
                )
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HOME_DRAWER_DESTINATIONS.forEach { replyDestination ->
                val isSelected = currentDestination.hasRoute(replyDestination)
                NavigationDrawerItem(
                    selected = isSelected,
                    label = {
                        Text(
                            text = stringResource(id = replyDestination.iconTextId),
                            modifier = Modifier.padding(horizontal = 16.dp),
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    icon = {
                        Icon(
                            imageVector = if (isSelected)
                                replyDestination.selectedIcon
                            else
                                replyDestination.unselectedIcon,
                            contentDescription = stringResource(
                                id = replyDestination.iconTextId
                            )
                        )
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        unselectedContainerColor = Color.Transparent
                    ),
                    onClick = { navigateToTopLevelDestination(replyDestination) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun MainNavigationRailPreview() {
    MainNavigationRail(
        currentDestination = null,
        navigateToTopLevelDestination = {
        },
        onDrawerClicked = {
        }
    )
}

@Preview
@Composable
private fun MainBottomNavigationBarPreview() {
    MainBottomNavigationBar(
        currentDestination = null,
        navigateToTopLevelDestination = {
        }
    )
}

@Preview
@Composable
private fun MailMainNavigationDrawerPreview() {
    MailMainNavigationDrawer(
        currentDestination = null,
        navigateToTopLevelDestination = {},
        onDrawerClicked = {}
    )
}