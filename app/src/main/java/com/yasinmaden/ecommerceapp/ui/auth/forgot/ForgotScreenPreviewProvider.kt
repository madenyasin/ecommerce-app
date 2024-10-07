package com.yasinmaden.ecommerceapp.ui.auth.forgot

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ForgotScreenPreviewProvider : PreviewParameterProvider<ForgotContract.UiState> {
    override val values: Sequence<ForgotContract.UiState>
        get() = sequenceOf(
            ForgotContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            ForgotContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            ForgotContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}