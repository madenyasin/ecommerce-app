package com.yasinmaden.navigationss.di

import com.google.firebase.auth.FirebaseAuth
import com.yasinmaden.navigationss.data.repository.AuthRepository
import com.yasinmaden.navigationss.utils.GoogleSignInManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun bindAuthRepository(auth: FirebaseAuth, googleSignInManager: GoogleSignInManager): AuthRepository = AuthRepository(auth, googleSignInManager)
}