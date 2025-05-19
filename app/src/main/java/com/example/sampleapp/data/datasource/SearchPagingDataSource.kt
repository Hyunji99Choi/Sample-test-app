package com.example.sampleapp.data.datasource

import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.model.getOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class SearchPagingDataSource(
    private val search: String,
    private val apiService: SampleService
) : BasePagingDataSource<Search>() {

    override val startKey: Int = 1

    override suspend fun getPagingData(position: Int, size: Int): List<Search> {
        return withContext(Dispatchers.IO) {
            val resultImg = async {
                safeApiCall(
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
            }

            val resultVdo = async {
                safeApiCall(
                    call = {
                        apiService.getVideo(
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

            val images = resultImg.await()
            val videos = resultVdo.await()

            // 네트워크 통신 오류 처리
            if (images is SampleResult.Failure && videos is SampleResult.Failure) {
                throw Exception(images.error.getErrorMessage())
            }

            listOfNotNull(
                images.getOrNull(),
                videos.getOrNull()
            ).flatten().sortedByDescending { it.date }
        }
    }
}