package com.yasinmaden.navigationss.di

import com.yasinmaden.navigationss.data.repository.MainRepositoryImpl
import com.yasinmaden.navigationss.domain.repository.MainRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(repositoryImpl: MainRepositoryImpl): MainRepository
}