package com.yasinmaden.navigationss.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yasinmaden.navigationss.ui.components.BottomBarScreen
import com.yasinmaden.navigationss.ui.components.ScreenContent
import com.yasinmaden.navigationss.ui.detail.DetailScreen
import com.yasinmaden.navigationss.ui.detail.DetailViewModel
import com.yasinmaden.navigationss.ui.home.HomeScreen
import com.yasinmaden.navigationss.ui.home.HomeViewModel
import com.yasinmaden.navigationss.ui.profile.ProfileScreen
import com.yasinmaden.navigationss.ui.profile.ProfileViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    modifier: Modifier
) {

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Profile.route,
        modifier = modifier
    ) {
        composable(route = BottomBarScreen.Home.route) {
            val viewModel: HomeViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            HomeScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navController = navController,
                modifier = modifier
            )
        }
        composable(route = BottomBarScreen.Wishlist.route) {
            ScreenContent(
                name = BottomBarScreen.Wishlist.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Cart.route) {
            ScreenContent(
                name = BottomBarScreen.Cart.route,
                onClick = { }
            )
        }
        composable(route = BottomBarScreen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProfileScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navController = navController
            )
        }

        detailsNavGraph(navController = navController)
        authNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            val viewModel: DetailViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            DetailScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,
                navController = navController,
                modifier = Modifier
            )
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    data object Information : DetailsScreen(route = "INFORMATION")
    data object Overview : DetailsScreen(route = "OVERVIEW")
    data object Review : DetailsScreen(route = "REVIEW")

}