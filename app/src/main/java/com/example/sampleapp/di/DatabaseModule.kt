package com.example.sampleapp.di

import android.content.Context
import androidx.room.Room
import com.example.sampleapp.data.database.CachingDatabase
import com.example.sampleapp.data.database.StorageDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    /**
     * 즐겨찾기 및 로컬 데이터 DB
     */
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): StorageDatabase = Room.databaseBuilder(
        context,
        StorageDatabase::class.java,
        "app_db"
    ).fallbackToDestructiveMigration() // 버전 불일치 시 리셋, 버전 관리 가능
        .build()

    @Provides
    fun provideUserDao(storageDatabase: StorageDatabase) = storageDatabase.storageDataDao()


    /**
     * Data load 서포트 DB
     */
    @Singleton
    @Provides
    fun provideCachingDatabase(
        @ApplicationContext context: Context
    ): CachingDatabase = Room.databaseBuilder(
        context,
        CachingDatabase::class.java,
        "caching_db"
    ).fallbackToDestructiveMigration() // 버전 불일치 시 리셋
        .build()


    @Provides
    fun provideSearchListDao(database: CachingDatabase) = database.SearchListDao()

    @Provides
    fun provideSearchWordDao(database: CachingDatabase) = database.SearchWordDao()

    @Provides
    fun provideRemoteKeyDao(database: CachingDatabase) = database.remoteKeyDao()

}
