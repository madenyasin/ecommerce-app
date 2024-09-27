package com.yasinmaden.navigationss.ui.detail

import com.yasinmaden.navigationss.data.model.product.ProductDetails

object DetailContract {
    data class UiState(
        val isLoading: Boolean = false,
        val list: List<String> = emptyList(),
        val productDetail: ProductDetails = ProductDetails()
    )
    sealed class UiAction {
    }
    sealed class UiEffect {
    }
}