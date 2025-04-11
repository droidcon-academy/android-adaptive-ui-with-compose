package com.droidcon.adaptiveinbox.utils

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.droidcon.adaptiveinbox.model.MailDestination

fun NavDestination?.hasRoute(destination: MailDestination): Boolean =
    this?.hasRoute(destination.route::class) ?: false

class MailNavigationActions(
    private val navController: NavHostController
) {
    fun navigateTo(destination: MailDestination, shouldSaveRestoreState: Boolean = true) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = shouldSaveRestoreState
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = shouldSaveRestoreState
        }
    }
}