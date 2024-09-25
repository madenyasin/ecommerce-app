package com.yasinmaden.navigationss.domain.usecase

import com.yasinmaden.navigationss.common.Resource
import com.yasinmaden.navigationss.data.model.product.ProductResponse
import com.yasinmaden.navigationss.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(
    private val productRepository: ProductRepository
){
    suspend fun execute(): Resource<ProductResponse>{
        return productRepository.getProducts()
    }
}