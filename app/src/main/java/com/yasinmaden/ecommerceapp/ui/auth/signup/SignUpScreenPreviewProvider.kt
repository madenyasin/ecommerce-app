package com.yasinmaden.ecommerceapp.ui.auth.signup

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SignUpScreenPreviewProvider : PreviewParameterProvider<SignUpContract.UiState> {
    override val values: Sequence<SignUpContract.UiState>
        get() = sequenceOf(
            SignUpContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            SignUpContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            SignUpContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}