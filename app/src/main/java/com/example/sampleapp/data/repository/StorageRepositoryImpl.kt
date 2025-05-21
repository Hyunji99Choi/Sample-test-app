package com.example.sampleapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.sampleapp.data.database.StorageDataDao
import com.example.sampleapp.data.datasource.BasePagingDataSource
import com.example.sampleapp.data.model.local.toDomain
import com.example.sampleapp.data.model.local.toEntity
import com.example.sampleapp.domain.model.SampleError
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.StorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class StorageRepositoryImpl(
    private val dbDao: StorageDataDao
) : StorageRepository {

    override fun getStorageFlowData(): Flow<List<Search>> {
        return dbDao.getStorageFlow().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getStoragePagingData(): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(
                pageSize = BasePagingDataSource.pagingSize,
                enablePlaceholders = false,
                initialLoadSize = BasePagingDataSource.pagingSize
            ),
            pagingSourceFactory = {
                dbDao.getStoragePaging()
            }
        ).flow
            .map {
                it.map { i -> i.toDomain() }
            }
    }


    override suspend fun insertStorage(search: Search): SampleResult<Long> {
        return withContext(Dispatchers.IO) {
            try {
                SampleResult.Success(
                    dbDao.insertStorage(search.toEntity())
                )
            } catch (e: Exception) {
                SampleResult.Failure(
                    SampleError.DatabaseError(
                        e.message ?: "Unknown database error"
                    )
                )
            }
        }

    }

    override suspend fun deleteStorage(search: Search): SampleResult<Int> {
        return withContext(Dispatchers.IO) {
            try {
                SampleResult.Success(
                    dbDao.deleteStorage(search.getKey())
                )
            } catch (e: Exception) {
                SampleResult.Failure(
                    SampleError.DatabaseError(
                        e.message ?: "Unknown database error"
                    )
                )
            }
        }
    }


    override fun getStorageIdFlow(): Flow<List<String>> {
        return dbDao.getStorageIdFlow()
    }

    override suspend fun getStorage(id: String): Search? {
        return dbDao.getStorage(id)?.toDomain()
    }

}