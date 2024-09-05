package com.yasinmaden.navigationss.ui.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.OutlinedButton
import androidx.wear.compose.material.Switch
import com.yasinmaden.navigationss.R
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import com.yasinmaden.navigationss.ui.components.SocialMediaButton
import com.yasinmaden.navigationss.ui.login.LoginContract.UiAction
import com.yasinmaden.navigationss.ui.login.LoginContract.UiEffect
import com.yasinmaden.navigationss.ui.login.LoginContract.UiState
import com.yasinmaden.navigationss.ui.navigation.AuthScreen
import com.yasinmaden.navigationss.ui.navigation.Graph
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

                is UiEffect.NavigateToHome -> {
                    navController.navigate(Graph.HOME)
                }
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> LoginContent(
            email = uiState.email,
            password = uiState.password,
            onEmailChange = { onAction(UiAction.OnEmailChange(it)) },
            onPasswordChange = { onAction(UiAction.OnPasswordChange(it)) },
            onClick = { onAction(UiAction.OnLoginClick) },
            onSignUpClick = { onAction(UiAction.OnSignUpClick) },
            onForgotClick = { onAction(UiAction.OnForgotClick) }
        )
    }
}

@Composable
fun LoginContent(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
                text = "Welcome",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Please enter your data to continue",
                fontSize = 15.sp,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
            OutlinedTextField(
                value = email,
                onValueChange = onEmailChange,
                label = { Text(text = "Email", fontSize = 13.sp) },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
            )
            OutlinedTextField(
                value = password,
                onValueChange = onPasswordChange,
                label = { Text(text = "Password", fontSize = 13.sp) },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Join Us!",
                    fontSize = 15.sp,
                    color = Color.DarkGray,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { onSignUpClick.invoke() }
                )
                Text(
                    text = "Forgot Password?",
                    fontSize = 15.sp,
                    color = Color.Red,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { onForgotClick.invoke() }
                )
            }
            Text(
                "Or login with social account",
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SocialMediaButton(
                    modifier = Modifier,
                    onClick = {},
                    image = painterResource(R.drawable.google_logo),
                    name = "Google"
                )
                SocialMediaButton(
                    modifier = Modifier,
                    onClick = {},
                    image = painterResource(R.drawable.facebook_logo),
                    name = "Facebook"
                )
                SocialMediaButton(
                    modifier = Modifier,
                    onClick = {},
                    image = painterResource(R.drawable.github_logo),
                    name = "Github"
                )
            }
        }
        Button(
            onClick = onClick,
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align the button to the bottom center
                .fillMaxWidth() // Make the button full width
                .padding(top = 16.dp) // Optional padding for spacing
                .size(height = 75.dp, width = 150.dp)
        ) {
            Text(text = "Login", fontSize = 17.sp, style = MaterialTheme.typography.titleLarge)
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

