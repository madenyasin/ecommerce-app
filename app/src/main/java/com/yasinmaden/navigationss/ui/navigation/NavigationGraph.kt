//package com.yasinmaden.navigationss.ui.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import androidx.lifecycle.viewmodel.compose.viewModel
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import com.yasinmaden.navigationss.ui.forgot.ForgotScreen
//import com.yasinmaden.navigationss.ui.forgot.ForgotViewModel
//import com.yasinmaden.navigationss.ui.profile.ProfileScreen
//import com.yasinmaden.navigationss.ui.profile.ProfileViewModel
//import com.yasinmaden.navigationss.ui.settings.SettingsScreen
//import com.yasinmaden.navigationss.ui.settings.SettingsViewModel
//import com.yasinmaden.navigationss.ui.signup.SignUpScreen
//import com.yasinmaden.navigationss.ui.signup.SignUpViewModel
//
//@Composable
//fun NavigationGraph(
//    navController: NavHostController,
//    startDestination: String,
//    modifier: Modifier = Modifier,
//) {
//    NavHost(
//        modifier = Modifier.then(modifier),
//        navController = navController,
//        startDestination = startDestination,
//    ) {
//
//        composable("Profile") {
//            val viewModel = viewModel<ProfileViewModel>(it)
//            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//            val uiEffect = viewModel.uiEffect
//            ProfileScreen(
//                uiState = uiState,
//                uiEffect = uiEffect,
//                onAction = viewModel::onAction
//            )
//        }
//        composable("Settings") {
//            val viewModel = viewModel<SettingsViewModel>(it)
//            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//            val uiEffect = viewModel.uiEffect
//            SettingsScreen(
//                uiState = uiState,
//                uiEffect = uiEffect,
//                onAction = viewModel::onAction
//            )
//        }
////        composable("Login") {
////            val viewModel = viewModel<LoginViewModel>(it)
////            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
////            val uiEffect = viewModel.uiEffect
////            LoginScreen(
////                uiState = uiState,
////                uiEffect = uiEffect,
////                onAction = viewModel::onAction
////            )
////        }
//        composable("SignUp") {
//            val viewModel = viewModel<SignUpViewModel>(it)
//            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//            val uiEffect = viewModel.uiEffect
//            SignUpScreen(
//                uiState = uiState,
//                uiEffect = uiEffect,
//                onAction = viewModel::onAction
//            )
//        }
//        composable("Forgot") {
//            val viewModel = viewModel<ForgotViewModel>(it)
//            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//            val uiEffect = viewModel.uiEffect
//            ForgotScreen(
//                uiState = uiState,
//                uiEffect = uiEffect,
//                onAction = viewModel::onAction
//            )
//        }
//
//    }
//}