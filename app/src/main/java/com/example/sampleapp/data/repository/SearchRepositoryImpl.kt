package com.example.sampleapp.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.sampleapp.data.database.CachingDatabase
import com.example.sampleapp.data.datasource.BasePagingDataSource
import com.example.sampleapp.data.datasource.SearchPagingDataSource
import com.example.sampleapp.data.datasource.SearchRemoteMediator
import com.example.sampleapp.data.model.local.toDomain
import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl(
    private val api: SampleService,
    private val database: CachingDatabase
) : SearchRepository {


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

    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchCachingPagingData(search: String): Flow<PagingData<Search>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            remoteMediator = SearchRemoteMediator(search, api, database),
            pagingSourceFactory = {
                database.SearchListDao().pagingSource(search)
            }
        ).flow
            .map { paging ->
                paging.map {
                    it.toDomain()
                }
            }

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