package com.yasinmaden.navigationss.ui.login

object LoginContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
    )

    sealed class UiAction {
        object OnLoginClick : UiAction()
        object OnSignUpClick : UiAction()
        object OnForgotClick : UiAction()
    }


    sealed class UiEffect {
        object NavigateToSignUp : UiEffect()
        object NavigateToForgotPassword : UiEffect()
        object NavigateToHome : UiEffect()
    }
}