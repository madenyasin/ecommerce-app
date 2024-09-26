package com.yasinmaden.navigationss.domain.repository

import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductResponse
import com.yasinmaden.navigationss.data.network.DummyAPIService
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val dummyAPIService: DummyAPIService
){
    suspend fun getProducts(): Resource<ProductResponse> {
        val response = try {
            dummyAPIService.getProducts()
        }catch (e: Exception){
            return Resource.Error(exception = e)
        }
        return Resource.Success(data = response)
    }
}