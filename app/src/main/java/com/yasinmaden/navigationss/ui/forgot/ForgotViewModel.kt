package com.yasinmaden.navigationss.ui.forgot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yasinmaden.navigationss.ui.forgot.ForgotContract.UiAction
import com.yasinmaden.navigationss.ui.forgot.ForgotContract.UiEffect
import com.yasinmaden.navigationss.ui.forgot.ForgotContract.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForgotViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnBackClick -> handleBackClick()
            is UiAction.OnEmailChange -> updateEmail(uiAction.email)
            is UiAction.OnConfirmClick -> handleConfirmClick()
        }
    }
    private fun updateEmail(email: String){
        _uiState.update { it.copy(email = email) }
    }
    private fun handleBackClick() {
        viewModelScope.launch {
            emitUiEffect(UiEffect.NavigateBack)
        }
    }
    private fun handleConfirmClick() {
        viewModelScope.launch {
            emitUiEffect(UiEffect.NavigateToLogin)
            emitUiEffect(UiEffect.ShowToast("Password reset email sent"))
        }
    }



    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}