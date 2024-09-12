package com.yasinmaden.navigationss.ui.login

object LoginContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val email: String = "",
        val password: String = "",
    )
    sealed class UiAction {
        data object OnLoginClick : UiAction()
        data object OnSignUpClick : UiAction()
        data object OnForgotClick : UiAction()
        data class OnEmailChange(val email: String) : UiAction()   // New action for email input
        data class OnPasswordChange(val password: String) : UiAction() // New action for password input

        data class OnGoogleSignIn(val idToken: String) : UiAction() // New action
    }
    sealed class UiEffect {
        data object NavigateToSignUp : UiEffect()
        data object NavigateToForgotPassword : UiEffect()
        data object NavigateToHome : UiEffect()
        data class ShowToast(val message: String) : UiEffect()

    }
}