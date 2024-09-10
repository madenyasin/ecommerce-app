package com.yasinmaden.navigationss.ui.forgot

object ForgotContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val email: String = "",
    )

    sealed class UiAction{
        object OnConfirmClick : UiAction()
        object OnBackClick : UiAction()
        data class OnEmailChange(val email: String) : UiAction()   // New action for email input
    }

    sealed class UiEffect{
        object NavigateBack : UiEffect()
        object NavigateToLogin : UiEffect()
        data class ShowToast(val message: String) : UiEffect()


    }
}