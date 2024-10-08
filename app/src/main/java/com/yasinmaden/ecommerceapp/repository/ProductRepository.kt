package com.yasinmaden.ecommerceapp.repository

import com.yasinmaden.ecommerceapp.common.Resource
import com.yasinmaden.ecommerceapp.data.model.product.ProductDetails
import com.yasinmaden.ecommerceapp.data.model.product.ProductResponse
import com.yasinmaden.ecommerceapp.network.DummyAPIService
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