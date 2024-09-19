package com.yasinmaden.navigationss.ui.home

import com.yasinmaden.navigationss.ui.components.BottomBarScreen

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val currentTab: BottomBarScreen = BottomBarScreen.Home
    )

    sealed class UiAction {
        data class OnTabSelected(val screen: BottomBarScreen) : UiAction()
    }

    sealed class UiEffect {
        data class NavigateTo(val route: String) : UiEffect()
    }
}