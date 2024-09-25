package com.yasinmaden.navigationss.data.repository

import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.network.ApiService
import com.yasinmaden.navigationss.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): CategoryRepository{
    override suspend fun getCategories(): Resource<List<String>> {
        val response = try {
            apiService.getCategories()
        }catch (e: Exception){
            return Resource.Error(exception = e)
        }
        return Resource.Success(data = response)
    }
}