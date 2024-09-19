package com.yasinmaden.navigationss.ui.profile

object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction

    sealed class UiEffect{
        data class ShowToast(val message: String) : UiEffect()
    }
}