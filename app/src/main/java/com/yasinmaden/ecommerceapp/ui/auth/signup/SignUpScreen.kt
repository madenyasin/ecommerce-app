package com.yasinmaden.ecommerceapp.ui.auth.signup

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmaden.ecommerceapp.navigation.AuthScreen
import com.yasinmaden.ecommerceapp.ui.components.EmptyScreen
import com.yasinmaden.ecommerceapp.ui.components.LoadingBar
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpContract.UiAction
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpContract.UiEffect
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpContract.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun SignUpScreen(
    navController: NavHostController,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is UiEffect.NavigateToLogin -> {
                    navController.navigate(AuthScreen.Login.route)
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
        else -> SignUpContent(
            uiState = uiState,
            onAction = onAction,
        )
    }
}

@Composable
fun SignUpContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
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
                text = "Sign Up",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold
            )

            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onAction(UiAction.OnEmailChange(it)) },
                label = { Text(text = "Email", fontSize = 13.sp) },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = { onAction(UiAction.OnPasswordChange(it)) },
                label = { Text(text = "Password", fontSize = 13.sp) },
                singleLine = true,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            )
        }
        Button(
            onClick = { onAction(UiAction.OnSignUpClick) },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align the button to the bottom center
                .fillMaxWidth() // Make the button full width
                .padding(top = 16.dp) // Optional padding for spacing
                .size(height = 75.dp, width = 150.dp)
        ) {
            Text(text = "Sign Up", fontSize = 17.sp, style = MaterialTheme.typography.titleLarge)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview(
    @PreviewParameter(SignUpScreenPreviewProvider::class) uiState: UiState,
) {
    SignUpScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navController = NavHostController(LocalContext.current),
    )
}