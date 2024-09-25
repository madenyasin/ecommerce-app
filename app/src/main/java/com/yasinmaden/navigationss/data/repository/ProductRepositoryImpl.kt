package com.yasinmaden.navigationss.data.repository

import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductResponse
import com.yasinmaden.navigationss.data.network.ApiService
import com.yasinmaden.navigationss.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : ProductRepository {
    override suspend fun getProducts(): Resource<ProductResponse> {
        val response = try {
            apiService.getProducts()
        }catch (e: Exception){
            return Resource.Error(exception = e)
        }
        return Resource.Success(data = response)
    }

}