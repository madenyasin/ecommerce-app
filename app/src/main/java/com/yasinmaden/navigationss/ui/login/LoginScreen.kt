package com.yasinmaden.navigationss.ui.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import com.yasinmaden.navigationss.ui.login.LoginContract.UiAction
import com.yasinmaden.navigationss.ui.login.LoginContract.UiEffect
import com.yasinmaden.navigationss.ui.login.LoginContract.UiState
import com.yasinmaden.navigationss.ui.navigation.AuthScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LoginScreen(
    navController: NavHostController,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    // Observe UI effects (like navigation)
    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is UiEffect.NavigateToSignUp -> {
                    navController.navigate(AuthScreen.SignUp.route)
                }
                is UiEffect.NavigateToForgotPassword -> {
                    navController.navigate(AuthScreen.Forgot.route)
                }
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> LoginContent(
            onClick = { onAction(LoginContract.UiAction.OnLoginClick) },
            onSignUpClick = { onAction(LoginContract.UiAction.OnSignUpClick) },
            onForgotClick = { onAction(LoginContract.UiAction.OnForgotClick) }
        )
    }
}

@Composable
fun LoginContent(
    onClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onForgotClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Login Content",
                fontSize = 24.sp,
            )
            Button(onClick = onClick, modifier = Modifier.padding(top = 16.dp)) {
                Text(text = "Login")
            }
            Button(onClick = onSignUpClick, modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Sign Up")
            }
            Button(onClick = onForgotClick, modifier = Modifier.padding(top = 8.dp)) {
                Text(text = "Forgot Password")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview(
    @PreviewParameter(LoginScreenPreviewProvider::class) uiState: UiState,
) {
    LoginScreen(
        navController = NavHostController(LocalContext.current),
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
    )
}