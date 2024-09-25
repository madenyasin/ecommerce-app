package com.yasinmaden.navigationss.di

import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.data.network.ApiService
import com.yasinmaden.navigationss.data.repository.AuthRepository
import com.yasinmaden.navigationss.data.repository.ProductRepositoryImpl
import com.yasinmaden.navigationss.domain.repository.ProductRepository
import com.yasinmaden.navigationss.utils.GoogleSignInManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun bindAuthRepository(auth: FirebaseAuth, googleSignInManager: GoogleSignInManager): AuthRepository = AuthRepository(auth, googleSignInManager)


    @Singleton
    @Provides
    fun provideProductRepository(
        apiService: ApiService
    ) : ProductRepository{
        return ProductRepositoryImpl(apiService)
    }
}