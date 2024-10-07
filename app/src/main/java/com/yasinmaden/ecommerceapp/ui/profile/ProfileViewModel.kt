package com.yasinmaden.ecommerceapp.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.ecommerceapp.repository.GoogleAuthRepository
import com.yasinmaden.ecommerceapp.ui.profile.ProfileContract.UiAction
import com.yasinmaden.ecommerceapp.ui.profile.ProfileContract.UiEffect
import com.yasinmaden.ecommerceapp.ui.profile.ProfileContract.UiState
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
class ProfileViewModel @Inject constructor(
    private val googleAuthRepository: GoogleAuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }


    init {
        _uiState.update { it.copy(user = firebaseAuth.currentUser) }
    }

    private fun signOut() = viewModelScope.launch {
        try {
            firebaseAuth.signOut()
            googleAuthRepository.signOut()
            emitUiEffect(UiEffect.NavigateToAuth)
        } catch (e: Exception) {
            emitUiEffect(UiEffect.ShowToast(e.message.toString()))
        }
    }

    fun onAction(uiAction: UiAction) {
        when (uiAction) {
            is UiAction.OnSignOut -> signOut()
        }
    }



    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}