package com.yasinmaden.navigationss.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.domain.repository.GoogleAuthRepository
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiAction
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiEffect
import com.yasinmaden.navigationss.ui.profile.ProfileContract.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val googleAuthRepository: GoogleAuthRepository,
    val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<UiEffect>() }
    val uiEffect: Flow<UiEffect> by lazy { _uiEffect.receiveAsFlow() }


    fun onAction(uiAction: UiAction) {
    }

    private fun updateUiState(block: UiState.() -> UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: UiEffect) {
        _uiEffect.send(uiEffect)
    }
}