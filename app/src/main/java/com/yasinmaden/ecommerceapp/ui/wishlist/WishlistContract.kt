package com.yasinmaden.ecommerceapp.ui.wishlist

import com.yasinmaden.ecommerceapp.data.model.product.ProductDetails


object WishlistContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val wishlist: List<ProductDetails> = emptyList()
    )

    sealed class UiAction {
    }

    sealed class UiEffect {
    }
}