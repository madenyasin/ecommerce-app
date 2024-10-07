package com.yasinmaden.ecommerceapp.ui.auth.forgot

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.yasinmaden.ecommerceapp.R
import com.yasinmaden.ecommerceapp.navigation.AuthScreen
import com.yasinmaden.ecommerceapp.ui.auth.forgot.ForgotContract.UiAction
import com.yasinmaden.ecommerceapp.ui.auth.forgot.ForgotContract.UiEffect
import com.yasinmaden.ecommerceapp.ui.auth.forgot.ForgotContract.UiState
import com.yasinmaden.ecommerceapp.ui.components.EmptyScreen
import com.yasinmaden.ecommerceapp.ui.components.LoadingBar
import com.yasinmaden.ecommerceapp.ui.theme.Gray
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Composable
fun ForgotScreen(
    navController: NavHostController,
    uiState: UiState,
    uiEffect: Flow<UiEffect>,
    onAction: (UiAction) -> Unit,
) {
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        uiEffect.collect { effect ->
            when (effect) {
                is UiEffect.NavigateBack -> {
                    navController.popBackStack()
                }

                is UiEffect.NavigateToLogin -> {
                    navController.navigate(AuthScreen.Login.route) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
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
        else -> ForgotContent(
            uiState = uiState,
            onAction = onAction,
        )
    }
}

@Composable
fun ForgotContent(
    uiState: UiState,
    onAction: (UiAction) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            onClick = { onAction(UiAction.OnBackClick) },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(32.dp, 48.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_arrow_back_24),
                contentDescription = "Back"
            )
        }
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                painter = painterResource(R.drawable.img),
                contentDescription = null,
                Modifier.size(225.dp, 166.dp)
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { onAction(UiAction.OnEmailChange(it)) },
                label = { Text(text = "Email Address", fontSize = 13.sp) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Done
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                "Please write your email to receive a" +
                        "\nconfirmation code to set a new password.",
                textAlign = TextAlign.Center,
                color = Gray,
                fontSize = 13.sp
            )
        }
        Button(
            onClick = { onAction(UiAction.OnConfirmClick) },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .align(Alignment.BottomCenter) // Align the button to the bottom center
                .fillMaxWidth() // Make the button full width
                .padding(top = 16.dp) // Optional padding for spacing
                .size(height = 75.dp, width = 150.dp)
        ) {
            Text(
                text = "Confirm Mail",
                fontSize = 17.sp,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotScreenPreview(
    @PreviewParameter(ForgotScreenPreviewProvider::class) uiState: UiState,
) {
    ForgotScreen(
        uiState = uiState,
        uiEffect = emptyFlow(),
        onAction = {},
        navController = NavHostController(LocalContext.current),
    )
}