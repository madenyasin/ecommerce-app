package com.yasinmaden.navigationss.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.data.network.ApiService
import com.yasinmaden.navigationss.data.repository.CategoryRepositoryImpl
import com.yasinmaden.navigationss.data.repository.ProductRepositoryImpl
import com.yasinmaden.navigationss.domain.repository.CategoryRepository
import com.yasinmaden.navigationss.domain.repository.FirebaseAuthRepository
import com.yasinmaden.navigationss.domain.repository.GoogleAuthRepository
import com.yasinmaden.navigationss.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {


    @Singleton
    @Provides
    fun provideFirebaseAuthRepository(
        auth: FirebaseAuth
    ): FirebaseAuthRepository {
        return FirebaseAuthRepository(auth)
    }

    @Singleton
    @Provides
    fun provideGoogleAuthRepository(
        googleSignInClient: GoogleSignInClient
    ): GoogleAuthRepository {
        return GoogleAuthRepository(googleSignInClient)
    }

    @Singleton
    @Provides
    fun provideApplicationContext(
        @ApplicationContext context: Context
    ): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        apiService: ApiService
    ): ProductRepository {
        return ProductRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        apiService: ApiService
    ): CategoryRepository {
        return CategoryRepositoryImpl(apiService)
    }
}