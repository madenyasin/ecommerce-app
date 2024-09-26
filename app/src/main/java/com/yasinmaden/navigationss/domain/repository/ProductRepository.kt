package com.yasinmaden.navigationss.domain.repository

import android.util.Log
import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductDetails
import com.yasinmaden.navigationss.data.model.product.ProductResponse
import com.yasinmaden.navigationss.network.DummyAPIService
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

    suspend fun getProductById(id: Int): Resource<ProductDetails>{
        val response = try {
            dummyAPIService.getProductById(id)
        }catch (e: Exception){
            return Resource.Error(exception = e)
        }
        return Resource.Success(data = response)
    }

    suspend fun getProductsByCategory(categoryName: String): Resource<ProductResponse>{
        val response = try {
            dummyAPIService.getProductsByCategory(categoryName)
        }catch (e: Exception){
            return Resource.Error(exception = e)
        }
        return Resource.Success(data = response)
    }
}