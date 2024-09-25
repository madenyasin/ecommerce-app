package com.yasinmaden.navigationss.di

import com.yasinmaden.navigationss.domain.repository.ProductRepository
import com.yasinmaden.navigationss.domain.usecase.GetProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetProductUseCase(
        productRepository: ProductRepository
    ) : GetProductUseCase {
        return GetProductUseCase(productRepository)
    }
}