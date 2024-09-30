package com.yasinmaden.navigationss.ui.auth.login

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.yasinmaden.navigationss.R
import com.yasinmaden.navigationss.navigation.AuthScreen
import com.yasinmaden.navigationss.navigation.Graph
import com.yasinmaden.navigationss.ui.auth.login.LoginContract.UiAction
import com.yasinmaden.navigationss.ui.auth.login.LoginContract.UiEffect
import com.yasinmaden.navigationss.ui.auth.login.LoginContract.UiState
import com.yasinmaden.navigationss.ui.components.EmptyScreen
import com.yasinmaden.navigationss.ui.components.LoadingBar
import com.yasinmaden.navigationss.ui.theme.DarkGray
import com.yasinmaden.navigationss.ui.theme.GoogleButtonColor
import com.yasinmaden.navigationss.ui.theme.Gray
import com.yasinmaden.navigationss.ui.theme.Red
import com.yasinmaden.navigationss.ui.theme.White
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun LoginScreen(
    navController: NavHostController,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    val viewModel: LoginViewModel = hiltViewModel()
    val context = LocalContext.current

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

                is UiEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    when {
        uiState.isLoading -> LoadingBar()
        uiState.list.isNotEmpty() -> EmptyScreen()
        else -> LoginContent(
            uiState = uiState,
            onAction = onAction,
            viewModel = LoginViewModel(
                firebaseAuthRepository = viewModel.firebaseAuthRepository,
                googleAuthRepository = viewModel.googleAuthRepository
            )
        )
    }
}

@Composable
fun LoginContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
    viewModel: LoginViewModel
) {
    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        try {
            val account: GoogleSignInAccount? =
                GoogleSignIn.getSignedInAccountFromIntent(result.data).result
            account?.idToken?.let { idToken ->
                onAction(UiAction.OnGoogleSignIn(idToken))
            }
        } catch (e: Exception) {
            UiEffect.ShowToast(e.message.toString())
        }

    }

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
                color = Gray
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onAction(UiAction.OnEmailChange(it)) },
                label = { Text(text = "Email", fontSize = 13.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onAction(UiAction.OnPasswordChange(it)) },
                label = { Text(text = "Password", fontSize = 13.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),

                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "Join Us!",
                    fontSize = 15.sp,
                    color = DarkGray,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { onAction(UiAction.OnSignUpClick) }
                )
                Text(
                    text = "Forgot Password?",
                    fontSize = 15.sp,
                    color = Red,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .clickable { onAction(UiAction.OnForgotClick) }
                )
            }
            Text(
                "Or login with social account",
                color = Gray,
                modifier = Modifier.padding(top = 16.dp)
            )

            Button(
                onClick = { googleSignInLauncher.launch(viewModel.onGoogleSignInIntent()) },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoogleButtonColor),
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
                    .size(height = 55.dp, width = 150.dp)

            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center

                ) {
                    Image(
                        painter = painterResource(R.drawable.google_logo),
                        null,
                        modifier = Modifier.size(15.dp),
                        colorFilter = ColorFilter.tint(White)
                    )
                    Text(
                        text = "Google",
                        modifier = Modifier.padding(start = 8.dp),
                        fontSize = 17.sp
                    )
                }
            }


        }
        Button(
            onClick = { onAction(UiAction.OnLoginClick) },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(top = 16.dp)
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
