package com.yasinmaden.navigationss.domain.repository

import com.yasinmaden.navigationss.common.Resource

interface CategoryRepository {
    suspend fun getCategories(): Resource<List<String>>
}