package com.example.sampleapp.data.datasource

import com.example.sampleapp.data.model.local.SearchType
import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.getOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WebSearchPagingDataSource(
    private val search: String,
    private val type: SearchType,
    private val apiService: SampleService
) : BasePagingDataSource<ItemData>() {

    override val startKey: Int = 1

    override suspend fun getPagingData(position: Int, size: Int): List<ItemData> {
        return withContext(Dispatchers.IO) {
            val result = when (type) {
                SearchType.BLOG -> {
                    getBlogData(search, position, size)
                }

                SearchType.CAFE -> {
                    getCafeData(search, position, size)
                }

                else -> {
                    getWebData(search, position, size)
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
        size: Int
    ): SampleResult<List<ItemData>> {
        return safeApiCall(
            call = {
                apiService.getBlog(
                    search = search,
                    page = position,
                    size = size,
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
        size: Int
    ): SampleResult<List<ItemData>> {
        return safeApiCall(
            call = {
                apiService.getCafe(
                    search = search,
                    page = position,
                    size = size,
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
        size: Int
    ): SampleResult<List<ItemData>> {
        return safeApiCall(
            call = {
                apiService.getWeb(
                    search = search,
                    page = position,
                    size = size,
                )
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )
    }

}