package com.example.sampleapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.sampleapp.data.database.StorageDataDao
import com.example.sampleapp.data.database.StorageDatabase
import com.example.sampleapp.data.datasource.BasePagingDataSource
import com.example.sampleapp.data.datasource.SamplePagingDataSource
import com.example.sampleapp.data.datasource.SearchPagingDataSource
import com.example.sampleapp.data.model.local.toDomain
import com.example.sampleapp.data.model.local.toEntity
import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.SampleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SampleRepositoryImpl(
    private val api: SampleService,
    private val dbDao: StorageDataDao
) : SampleRepository {

    override suspend fun getData() {

    }

    override fun getPagingData(): Pager<Int, Search> {
        return Pager(
            config = PagingConfig(
                pageSize = BasePagingDataSource.pagingSize,
                enablePlaceholders = false,
                initialLoadSize = BasePagingDataSource.pagingSize
            ),
            pagingSourceFactory = {
                SamplePagingDataSource(
                    search = "test",
                    apiService = api
                )
            }
        )
    }


    override fun getSearchPagingData(search: String): Pager<Int, Search> {
        return Pager(
            config = PagingConfig(
                pageSize = BasePagingDataSource.pagingSize,
                enablePlaceholders = false,
                initialLoadSize = BasePagingDataSource.pagingSize
            ),
            pagingSourceFactory = {
                SearchPagingDataSource(
                    search = search,
                    apiService = api
                )
            }
        )
    }


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


    override suspend fun getStorage(id: String): Search? {
        return dbDao.getStorage(id)?.toDomain()
    }

    override suspend fun insertStorage(search: Search): Long {
        return withContext(Dispatchers.IO) {
            dbDao.insertStorage(search.toEntity())
        }

    }

    override suspend fun deleteStorage(search: Search): Int {
        return withContext(Dispatchers.IO) {
            dbDao.deleteStorage(search.getKey())
        }
    }


    override fun getStorageIdFlow(): Flow<List<String>> {
        return dbDao.getStorageIdFlow()
    }


    override suspend fun getSearchImage(
        search: String,
        page: Int,
        size: Int
    ): SampleResult<List<Search>> {
        return safeApiCall(
            call = {
                api.getImage(search, page = page, size = size)
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )
    }


    override suspend fun getSearchVideo(
        search: String,
        page: Int,
        size: Int
    ): SampleResult<List<Search>> {
        return safeApiCall(
            call = {
                api.getVideo(search = search, page = page, size = size)
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )
    }
}