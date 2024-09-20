package com.yasinmaden.navigationss.ui.home


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigation
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.Text
import com.yasinmaden.navigationss.navigation.HomeNavGraph
import com.yasinmaden.navigationss.ui.components.BottomBarScreen
import com.yasinmaden.navigationss.ui.theme.NavigationItemTextColor
import com.yasinmaden.navigationss.ui.theme.NavigationItemTintColor
import com.yasinmaden.navigationss.ui.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BaseHomeScreen(navController: NavHostController = rememberNavController()) {
    Scaffold(
        bottomBar = { BottomBar(navController = navController) }
    ) {
        HomeNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavHostController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Wishlist,
        BottomBarScreen.Cart,
        BottomBarScreen.Profile,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        BottomNavigation(
            backgroundColor = White,
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val isSelected = currentDestination?.hierarchy?.any {
        it.route == screen.route
    } == true

    BottomNavigationItem(
        icon = {
            if (isSelected) {
                Text(text = screen.title, color = NavigationItemTextColor)
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(screen.iconRes),
                    contentDescription = "Navigation Icon",
                    tint = NavigationItemTintColor
                )
            }
        },
        selected = isSelected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}