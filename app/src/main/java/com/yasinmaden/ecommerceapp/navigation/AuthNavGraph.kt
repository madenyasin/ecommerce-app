package com.yasinmaden.ecommerceapp.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.yasinmaden.ecommerceapp.ui.auth.forgot.ForgotScreen
import com.yasinmaden.ecommerceapp.ui.auth.forgot.ForgotViewModel
import com.yasinmaden.ecommerceapp.ui.auth.login.LoginScreen
import com.yasinmaden.ecommerceapp.ui.auth.login.LoginViewModel
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpScreen
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpViewModel

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.AUTHENTICATION,
        startDestination = AuthScreen.Login.route
    ) {
        composable(route = AuthScreen.Login.route) {
            val viewModel: LoginViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            LoginScreen(
                navController = navController,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction,

            )
        }
        composable(route = AuthScreen.Forgot.route) {
            val viewModel: ForgotViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect
            ForgotScreen(
                navController = navController,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
        composable(route = AuthScreen.SignUp.route) {
            val viewModel: SignUpViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val uiEffect = viewModel.uiEffect

            SignUpScreen(
                navController = navController,
                uiState = uiState,
                uiEffect = uiEffect,
                onAction = viewModel::onAction
            )
        }
    }
}

sealed class AuthScreen(val route: String) {
    data object Login : AuthScreen(route = "LOGIN")
    data object SignUp : AuthScreen(route = "SIGN_UP")
    data object Forgot : AuthScreen(route = "FORGOT")
}