package com.example.sampleapp.di

import com.example.sampleapp.data.database.CachingDatabase
import com.example.sampleapp.data.database.StorageDataDao
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.repository.SearchRepositoryImpl
import com.example.sampleapp.data.repository.StorageRepositoryImpl
import com.example.sampleapp.domain.repository.SearchRepository
import com.example.sampleapp.domain.repository.StorageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideSearchRepository(api: SampleService, db: CachingDatabase): SearchRepository {
        return SearchRepositoryImpl(api, db)
    }

    @Provides
    fun provideStorageRepository(db: StorageDataDao): StorageRepository {
        return StorageRepositoryImpl(db)
    }

}