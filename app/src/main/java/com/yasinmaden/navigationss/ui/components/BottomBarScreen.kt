package com.yasinmaden.navigationss.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    data object Home : BottomBarScreen(
        route = "HOME",
        title = "HOME",
        icon = Icons.Default.Home
    )

    data object Profile : BottomBarScreen(
        route = "PROFILE",
        title = "PROFILE",
        icon = Icons.Default.Person
    )

    data object Settings : BottomBarScreen(
        route = "SETTINGS",
        title = "SETTINGS",
        icon = Icons.Default.Settings
    )
}