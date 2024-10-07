package com.yasinmaden.ecommerceapp.ui.auth.forgot

object ForgotContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val email: String = "",
    )

    sealed class UiAction{
        data object OnConfirmClick : UiAction()
        data object OnBackClick : UiAction()
        data class OnEmailChange(val email: String) : UiAction()   // New action for email input
    }

    sealed class UiEffect{
        data object NavigateBack : UiEffect()
        data object NavigateToLogin : UiEffect()
        data class ShowToast(val message: String) : UiEffect()


    }
}