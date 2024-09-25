package com.yasinmaden.navigationss.domain.usecase

import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.domain.repository.CategoryRepository
import javax.inject.Inject

class GetCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
){
    suspend fun execute(): Resource<List<String>> {
        return categoryRepository.getCategories()
    }

}