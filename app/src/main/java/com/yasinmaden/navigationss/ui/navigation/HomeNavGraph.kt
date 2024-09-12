package com.yasinmaden.navigationss.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.MaterialTheme
import com.yasinmaden.navigationss.di.FirebaseModule.provideFirebaseAuth
import com.yasinmaden.navigationss.ui.components.BottomBarScreen
import com.yasinmaden.navigationss.ui.components.ScreenContent
import com.yasinmaden.navigationss.ui.profile.ProfileScreen
import com.yasinmaden.navigationss.ui.profile.ProfileViewModel
import com.yasinmaden.navigationss.utils.GoogleSignInManager

@Composable
fun HomeNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Profile.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            ScreenContent(
                name = BottomBarScreen.Home.route,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Button(
                    onClick = {
                        provideFirebaseAuth().signOut()
                        // googleSignInClient.signOut()
                        GoogleSignInManager(
                            context = navController.context
                        ).signOut()
                        // Navigate to the Authentication screen and clear the back stack
                        navController.navigate(Graph.AUTHENTICATION) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive =
                                    true // Removes everything from the back stack, including the current destination
                            }

                        }
                    }
                ) {
                    Text(
                        text = "Sign Out",
                        fontSize = MaterialTheme.typography.title1.fontSize,
                    )
                }
            }
        }
        composable(route = BottomBarScreen.Profile.route) {
            val viewModel: ProfileViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ProfileScreen(
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable(route = BottomBarScreen.Settings.route) {
            ScreenContent(
                name = BottomBarScreen.Settings.route,
                onClick = { }
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
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
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
}