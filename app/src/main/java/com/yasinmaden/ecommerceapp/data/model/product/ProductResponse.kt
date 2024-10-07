package com.yasinmaden.ecommerceapp.data.model.product

data class ProductResponse(
    val limit: Int,
    val products: List<ProductDetails>,
    val skip: Int,
    val total: Int
)