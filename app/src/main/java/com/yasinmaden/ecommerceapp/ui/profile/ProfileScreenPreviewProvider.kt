package com.yasinmaden.ecommerceapp.ui.profile

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ProfileScreenPreviewProvider : PreviewParameterProvider<ProfileContract.UiState> {
    override val values: Sequence<ProfileContract.UiState>
        get() = sequenceOf(
            ProfileContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            ProfileContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            ProfileContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}