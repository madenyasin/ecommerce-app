package com.yasinmaden.ecommerceapp.ui.profile

import com.google.firebase.auth.FirebaseUser

object ProfileContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val user: FirebaseUser? = null
    )

    sealed class UiAction{
        data object OnSignOut : UiAction()
    }

    sealed class UiEffect{
        data class ShowToast(val message: String) : UiEffect()
        data object NavigateToAuth : UiEffect()
    }
}