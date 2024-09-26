package com.yasinmaden.navigationss.ui.home

import com.yasinmaden.navigationss.data.model.product.ProductDetails
import com.yasinmaden.navigationss.ui.components.BottomBarScreen

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val isLoadingProducts: Boolean = false,
        val list: List<String> = emptyList(),
        val currentTab: BottomBarScreen = BottomBarScreen.Home,
        val products: List<ProductDetails> = emptyList(),
        val categories: List<String> = emptyList()
    )

    sealed class UiAction {
        data class OnTabSelected(val screen: BottomBarScreen) : UiAction()
        data class OnCategorySelected(val category: String) : UiAction()
    }

    sealed class UiEffect {
        data class NavigateTo(val route: String) : UiEffect()
    }
}