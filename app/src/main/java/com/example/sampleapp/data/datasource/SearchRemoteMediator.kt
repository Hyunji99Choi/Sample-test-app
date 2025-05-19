package com.example.sampleapp.data.datasource

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.sampleapp.data.database.CachingDatabase
import com.example.sampleapp.data.model.local.SearchEntity
import com.example.sampleapp.data.model.local.SearchWordEntity
import com.example.sampleapp.data.model.local.toEntity
import com.example.sampleapp.data.model.remote.toDomain
import com.example.sampleapp.data.network.SampleService
import com.example.sampleapp.data.safeApiCall
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.getOrNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val keyword: String,
    private val apiService: SampleService,
    private val database: CachingDatabase
) : RemoteMediator<Int, SearchEntity>() {

    protected val cacheExpiryMillis = 5 * 60 * 1000L // 5분
    private val now get() = System.currentTimeMillis()

    private val startKey = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, SearchEntity>): MediatorResult {
        return try {
            Log.d("paging", "load")
            // 만료된 캐시 데이터 삭제
            val expiryTime = now - cacheExpiryMillis
            database.withTransaction {
                database.SearchListDao().deleteExpiredEntries(expiryTime)
                database.SearchWordDao().deleteExpiredInfo(expiryTime)
            }


            val savedData: SearchWordEntity? = database.SearchWordDao().getSavedWord(keyword)
            Log.d("paging", "savedData ${savedData}")


            val size = state.config.pageSize


            val position = when (loadType) {
                LoadType.REFRESH -> {
                    Log.d("paging", "LoadType.REFRESH")

                    // REFRESH 시 (검색어 변경/새로고침)
                    if (savedData != null) {
                        // Room 데이터 캐싱
                        return MediatorResult.Success(endOfPaginationReached = false)
                    }

                    startKey
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    savedData?.currentPage?.plus(1) ?: startKey
                }
            }

            Log.d("paging", "position: ${position}")


            withContext(Dispatchers.IO) {
                // 서버 데이터 호출
                Log.d("paging", "SearchRemoteMediator 서버 데이터 호출")
                val resultImg = async {
                    safeApiCall(
                        call = {
                            apiService.getImage(
                                search = keyword,
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
                                search = keyword,
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

                val searchList = listOfNotNull(
                    images.getOrNull(),
                    videos.getOrNull()
                ).flatten().sortedByDescending { it.date }


                // 캐시 데이터 적재
                database.withTransaction {
                    val savedTime = savedData?.lastUpdated ?: now

                    database.SearchWordDao().insert(SearchWordEntity(keyword, position, savedTime))
                    database.SearchListDao().insertAll(
                        searchList.map {
                            it.toEntity(keyword, savedTime)
                        }
                    )
                }

                MediatorResult.Success(endOfPaginationReached = searchList.isEmpty())
            }
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }


}