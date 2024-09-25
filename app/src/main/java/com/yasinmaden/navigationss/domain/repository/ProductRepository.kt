package com.yasinmaden.navigationss.domain.repository

import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductResponse

interface ProductRepository {
    suspend fun getProducts(): Resource<ProductResponse>
}