package com.yasinmaden.navigationss.data.network

import com.yasinmaden.navigationss.data.model.product.ProductResponse
import retrofit2.http.GET

interface ApiService {
    @GET("products")
    suspend fun getProducts(): ProductResponse
}