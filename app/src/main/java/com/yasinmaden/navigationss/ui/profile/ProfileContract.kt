package com.yasinmaden.navigationss.ui.profile

import com.google.firebase.auth.FirebaseUser
import com.yasinmaden.navigationss.ui.auth.login.LoginContract.UiEffect

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