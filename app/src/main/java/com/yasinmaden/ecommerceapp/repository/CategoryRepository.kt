package com.yasinmaden.ecommerceapp.repository

import com.yasinmaden.ecommerceapp.common.Resource
import com.yasinmaden.ecommerceapp.network.DummyAPIService
import javax.inject.Inject


class CategoryRepository @Inject constructor(
    private val dummyAPIService: DummyAPIService
){
    suspend fun getCategories(): Resource<List<String>>{
        val response = try {
            dummyAPIService.getCategories()
        }catch (e: Exception){
            return Resource.Error(exception = e)
        }
        return Resource.Success(data = response)
    }
}