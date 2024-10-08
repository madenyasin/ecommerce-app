package com.yasinmaden.ecommerceapp.ui.home

import com.yasinmaden.ecommerceapp.data.model.product.ProductDetails
import com.yasinmaden.ecommerceapp.ui.components.BottomBarScreen

object HomeContract {
    data class UiState(
        val isLoading: Boolean = false,
        val isLoadingProducts: Boolean = false,
        val list: List<String> = emptyList(),
        val currentTab: BottomBarScreen = BottomBarScreen.Home,
        val products: List<ProductDetails> = emptyList(),
        val product: ProductDetails = ProductDetails(),
        val categories: List<String> = emptyList()
    )

    sealed class UiAction {
        data class OnTabSelected(val screen: BottomBarScreen) : UiAction()
        data class OnCategorySelected(val category: String) : UiAction()
        data class OnProductSelected(val product: ProductDetails) : UiAction()
        data class OnFavoriteClicked(val product: ProductDetails) : UiAction()

    }

    sealed class UiEffect {
        data class NavigateTo(val route: String) : UiEffect()
        data class NavigateToProductDetails(val product: ProductDetails) : UiEffect()
    }
}