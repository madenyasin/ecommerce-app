package com.yasinmaden.ecommerceapp.ui.auth.signup

object SignUpContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val email: String = "",
        val password: String = "",
    )

    sealed class UiAction{
        data class OnEmailChange(val email: String): UiAction()
        data class OnPasswordChange(val password: String): UiAction()
        object OnSignUpClick: UiAction()
    }

    sealed class UiEffect{
        data object NavigateToLogin : UiEffect()
        data class ShowToast(val message: String) : UiEffect()
    }
}