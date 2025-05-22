package com.example.sampleapp.data.datasource

import com.example.sampleapp.data.model.local.SearchType
import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.SortType
import com.example.sampleapp.domain.model.getOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebSearchPagingDataSource( // TODO 분리할 수 있으면 분리하기
    private val search: String,
    private val sort: SortType,
    private val type: SearchType,
    private val apiService: SampleService
) : BasePagingDataSource<ItemData>() {

    override val startKey: Int = 1

    override suspend fun getPagingData(position: Int, size: Int): List<ItemData> {
        return withContext(Dispatchers.IO) {
            val result = when (type) {
                SearchType.BLOG -> {
                    getBlogData(search, position, size, sort)
                }

                SearchType.CAFE -> {
                    getCafeData(search, position, size, sort)
                }

                else -> {
                    getWebData(search, position, size, sort)
                }
            }


            // 네트워크 통신 오류 처리
            if (result is SampleResult.Failure) {
                throw Exception(result.error.getErrorMessage())
            }

            result.getOrNull() ?: listOf()
        }
    }


    private suspend fun getBlogData(
        search: String,
        position: Int,
        size: Int,
        sort: SortType
    ): SampleResult<List<ItemData>> {
        return safeApiCall(
            call = {
                apiService.getBlog(
                    search = search,
                    page = position,
                    size = size,
                    sort = sort.label
                )
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )
    }

    suspend fun getCafeData(
        search: String,
        position: Int,
        size: Int,
        sort: SortType
    ): SampleResult<List<ItemData>> {
        return safeApiCall(
            call = {
                apiService.getCafe(
                    search = search,
                    page = position,
                    size = size,
                    sort = sort.label
                )
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )
    }

    suspend fun getWebData(
        search: String,
        position: Int,
        size: Int,
        sort: SortType,
    ): SampleResult<List<ItemData>> {
        return safeApiCall(
            call = {
                apiService.getWeb(
                    search = search,
                    page = position,
                    size = size,
                    sort = sort.label
                )
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )
    }

}