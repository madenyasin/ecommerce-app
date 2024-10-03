package com.yasinmaden.navigationss.ui.detail

import com.yasinmaden.navigationss.data.model.product.ProductDetails

object DetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val product: ProductDetails = ProductDetails(),
        val products: List<ProductDetails> = emptyList()
    )
    sealed class UiAction {
        data class OnFavoriteClicked(val product: ProductDetails) : UiAction()
    }
    sealed class UiEffect {
    }
}