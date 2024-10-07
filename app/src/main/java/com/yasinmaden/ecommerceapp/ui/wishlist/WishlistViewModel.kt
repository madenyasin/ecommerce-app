package com.yasinmaden.ecommerceapp.ui.wishlist

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.ecommerceapp.repository.FirebaseDatabaseRepository
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
class WishlistViewModel @Inject constructor(
    private val firebaseDatabaseRepository: FirebaseDatabaseRepository,
    firebaseAuth: FirebaseAuth
) : ViewModel() {
    private val _uiState = MutableStateFlow(WishlistContract.UiState())
    val uiState: StateFlow<WishlistContract.UiState> = _uiState.asStateFlow()

    private val _uiEffect by lazy { Channel<WishlistContract.UiEffect>() }
    val uiEffect: Flow<WishlistContract.UiEffect> by lazy { _uiEffect.receiveAsFlow() }

    init {
        firebaseDatabaseRepository.getAllWishlist(
            user = firebaseAuth.currentUser!!,
            callback = {
                updateUiState { copy(wishlist = it) }
            },
            onError = {  }
        )
    }

    fun onAction(uiAction: WishlistContract.UiAction) {

    }

    private fun updateUiState(block: WishlistContract.UiState.() -> WishlistContract.UiState) {
        _uiState.update(block)
    }

    private suspend fun emitUiEffect(uiEffect: WishlistContract.UiEffect) {
        _uiEffect.send(uiEffect)
    }

}