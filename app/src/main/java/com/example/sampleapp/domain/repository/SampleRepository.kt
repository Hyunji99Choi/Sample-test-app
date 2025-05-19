package com.example.sampleapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface SampleRepository {

    suspend fun getData()

    fun getPagingData(): Pager<Int, Search>

    fun getSearchPagingData(search: String): Pager<Int, Search>


    fun getStoragePagingData(): Flow<PagingData<Search>>
    fun getStorageFlowData(): Flow<List<Search>>
    suspend fun insertStorage(search: Search): Long
    suspend fun deleteStorage(search: Search): Int
    suspend fun getStorage(id: String): Search?
    fun getStorageIdFlow(): Flow<List<String>>


    suspend fun getSearchImage(search: String, page: Int, size: Int): SampleResult<List<Search>>
    suspend fun getSearchVideo(search: String, page: Int, size: Int): SampleResult<List<Search>>

}