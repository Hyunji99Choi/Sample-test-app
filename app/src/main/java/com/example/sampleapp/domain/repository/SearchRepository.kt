package com.example.sampleapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchPagingData(search: String): Pager<Int, Search>
    fun getSearchCachingPagingData(search: String): Flow<PagingData<Search>>


    suspend fun getSearchImage(search: String, page: Int, size: Int): SampleResult<List<Search>>
    suspend fun getSearchVideo(search: String, page: Int, size: Int): SampleResult<List<Search>>

}