package com.yasinmaden.ecommerceapp.ui.auth.login

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class LoginScreenPreviewProvider : PreviewParameterProvider<LoginContract.UiState> {
    override val values: Sequence<LoginContract.UiState>
        get() = sequenceOf(
            LoginContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            LoginContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            LoginContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}