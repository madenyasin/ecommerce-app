package com.yasinmaden.navigationss.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.domain.repository.FirebaseAuthRepository
import com.yasinmaden.navigationss.ui.login.LoginContract.UiAction
import com.yasinmaden.navigationss.ui.login.LoginContract.UiEffect
import com.yasinmaden.navigationss.ui.login.LoginContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
) :
    ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }


    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnLoginClick -> signIn()
            is UiAction.OnSignUpClick -> handleSignUpClick()
            is UiAction.OnForgotClick -> handleForgotClick()
            is UiAction.OnEmailChange -> updateEmail(uiAction.email)
            is UiAction.OnPasswordChange -> updatePassword(uiAction.password)
            is UiAction.OnGoogleSignIn -> signInWithGoogle(uiAction.idToken)
        }
    }

    init {
        isUserLoggedIn()
    }

    fun onGoogleSignInIntent() = firebaseAuthRepository.getSignInIntent()

    private fun signIn() = viewModelScope.launch {
        when (val result = firebaseAuthRepository.signIn(uiState.value.email, uiState.value.password)) {
            is Resource.Success -> {
                emitUiEffect(UiEffect.ShowToast(result.data))
                emitUiEffect(UiEffect.NavigateToHome)
            }

            is Resource.Error -> {
                emitUiEffect(UiEffect.ShowToast(result.exception.message ?: "Error"))
            }
        }
    }
    private fun signInWithGoogle(idToken: String) = viewModelScope.launch {
        when (val result = firebaseAuthRepository.signInWithGoogle(idToken)) {
            is Resource.Success -> {
                emitUiEffect(UiEffect.ShowToast(result.data))
                emitUiEffect(UiEffect.NavigateToHome)
            }

            is Resource.Error -> {
                emitUiEffect(UiEffect.ShowToast(result.exception.message.orEmpty()))
            }
        }
    }



    private fun isUserLoggedIn() = viewModelScope.launch {
        if (firebaseAuthRepository.isUserLoggedIn()) {
            emitUiEffect(UiEffect.NavigateToHome)
        }
    }

    private fun updateEmail(email: String) {
        _uiState.update { it.copy(email = email) }
    }

    private fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
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