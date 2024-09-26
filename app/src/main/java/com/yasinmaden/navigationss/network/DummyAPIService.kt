package com.yasinmaden.navigationss.network

import com.yasinmaden.navigationss.data.model.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface DummyAPIService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 0): ProductResponse

    @GET("products/category-list")
    suspend fun getCategories(): List<String>
}