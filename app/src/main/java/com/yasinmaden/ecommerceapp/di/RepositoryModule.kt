package com.yasinmaden.ecommerceapp.di

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.yasinmaden.ecommerceapp.network.DummyAPIService
import com.yasinmaden.ecommerceapp.repository.CategoryRepository
import com.yasinmaden.ecommerceapp.repository.FirebaseAuthRepository
import com.yasinmaden.ecommerceapp.repository.FirebaseDatabaseRepository
import com.yasinmaden.ecommerceapp.repository.GoogleAuthRepository
import com.yasinmaden.ecommerceapp.repository.ProductRepository
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
        dummyAPIService: DummyAPIService
    ): ProductRepository {
        return ProductRepository(dummyAPIService)
    }

    @Singleton
    @Provides
    fun provideCategoryRepository(
        dummyAPIService: DummyAPIService
    ): CategoryRepository {
        return CategoryRepository(dummyAPIService)
    }

    @Singleton
    @Provides
    fun provideFirebaseDatabaseRepository(
        databaseReference: DatabaseReference
    ): FirebaseDatabaseRepository {
        return FirebaseDatabaseRepository(databaseReference)
    }
}