package com.yasinmaden.ecommerceapp.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasinmaden.ecommerceapp.common.Resource
import com.yasinmaden.ecommerceapp.repository.FirebaseAuthRepository
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpContract.UiAction
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpContract.UiEffect
import com.yasinmaden.ecommerceapp.ui.auth.signup.SignUpContract.UiState
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
class SignUpViewModel @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnEmailChange -> updateUiState { copy(email = uiAction.email) }
            is UiAction.OnPasswordChange -> updateUiState { copy(password = uiAction.password) }
            is UiAction.OnSignUpClick -> signUp()

        }
    }
    private fun signUp() = viewModelScope.launch {
        when (val result = firebaseAuthRepository.signUp(uiState.value.email, uiState.value.password)) {
            is Resource.Success -> {
                emitUiEffect(UiEffect.ShowToast(result.data))
                emitUiEffect(UiEffect.NavigateToLogin)
            }

            is Resource.Error -> {
                emitUiEffect(UiEffect.ShowToast(result.exception.message.orEmpty()))
            }
        }
    }
    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}