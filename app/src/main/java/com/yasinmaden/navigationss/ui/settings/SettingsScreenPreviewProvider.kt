package com.yasinmaden.navigationss.ui.settings

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class SettingsScreenPreviewProvider : PreviewParameterProvider<SettingsContract.UiState> {
    override val values: Sequence<SettingsContract.UiState>
        get() = sequenceOf(
            SettingsContract.UiState(
                isLoading = true,
                list = emptyList(),
            ),
            SettingsContract.UiState(
                isLoading = false,
                list = emptyList(),
            ),
            SettingsContract.UiState(
                isLoading = false,
                list = listOf("Item 1", "Item 2", "Item 3")
            ),
        )
}