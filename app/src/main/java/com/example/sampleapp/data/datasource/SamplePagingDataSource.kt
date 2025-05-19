package com.example.sampleapp.data.datasource

import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search

class SamplePagingDataSource(
    private val search: String,
    private val apiService: SampleService
): BasePagingDataSource<Search>() {
    override suspend fun getPagingData(position: Int, size: Int): List<Search> {
        val result = safeApiCall(
            call = {
                apiService.getImage(
                    search = search,
                    page = position,
                    size = size,
                )
            },
            transform = { data ->
                data.documents.map { it.toDomain() }
            }
        )

        return when (result) {
            is SampleResult.Success -> result.data
            else -> emptyList()
        }

    }
}