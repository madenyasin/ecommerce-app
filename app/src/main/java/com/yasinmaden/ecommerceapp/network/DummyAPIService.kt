package com.yasinmaden.ecommerceapp.network

import com.yasinmaden.ecommerceapp.data.model.product.ProductDetails
import com.yasinmaden.ecommerceapp.data.model.product.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DummyAPIService {
    @GET("products")
    suspend fun getProducts(@Query("limit") limit: Int = 0): ProductResponse

    @GET("products/category/{categoryName}")
    suspend fun getProductsByCategory(@Path("categoryName") categoryName: String): ProductResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDetails

    @GET("products/category-list")
    suspend fun getCategories(): List<String>
}