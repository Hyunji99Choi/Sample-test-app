package com.example.sampleapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    fun getStoragePagingData(): Flow<PagingData<Search>>
    fun getStorageFlowData(): Flow<List<Search>>

    suspend fun insertStorage(search: Search): SampleResult<Long>
    suspend fun deleteStorage(search: Search): SampleResult<Int>

    fun getStorageIdFlow(): Flow<List<String>>
    suspend fun getStorage(id: String): Search?
}