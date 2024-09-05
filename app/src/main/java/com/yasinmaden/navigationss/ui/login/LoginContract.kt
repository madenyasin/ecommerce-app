package com.yasinmaden.navigationss.ui.login

object LoginContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val email: String = "",
        val password: String = "",
    )

    sealed class UiAction {
        object OnLoginClick : UiAction()
        object OnSignUpClick : UiAction()
        object OnForgotClick : UiAction()
        data class OnEmailChange(val email: String) : UiAction()   // New action for email input
        data class OnPasswordChange(val password: String) : UiAction() // New action for password input


    }


    sealed class UiEffect {
        object NavigateToSignUp : UiEffect()
        object NavigateToForgotPassword : UiEffect()
        object NavigateToHome : UiEffect()
    }
}