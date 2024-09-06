package com.yasinmaden.navigationss.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.navigationss.ui.login.LoginContract.UiAction
import com.yasinmaden.navigationss.ui.login.LoginContract.UiEffect
import com.yasinmaden.navigationss.ui.login.LoginContract.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }


    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnLoginClick -> handleLoginClick()
            is UiAction.OnSignUpClick -> handleSignUpClick()
            is UiAction.OnForgotClick -> handleForgotClick()
            is UiAction.OnEmailChange -> updateEmail(uiAction.email)
            is UiAction.OnPasswordChange -> updatePassword(uiAction.password)
        }
    }
    private fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }


    private fun handleLoginClick() {
        // Handle login logic here, e.g., authentication
        // Update state or emit effects
        viewModelScope.launch {
            emitUiEffect(UiEffect.NavigateToHome)
        }
    }

    private fun handleSignUpClick() {
        // Handle sign-up logic here, e.g., navigate to sign-up screen
        viewModelScope.launch {
            emitUiEffect(UiEffect.NavigateToSignUp)
        }
    }

    private fun handleForgotClick() {
        // Handle forgot password logic here, e.g., navigate to reset password screen
        viewModelScope.launch {
            emitUiEffect(UiEffect.NavigateToForgotPassword)
        }
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}