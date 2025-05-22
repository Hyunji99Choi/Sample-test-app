package com.example.sampleapp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingData
import com.example.sampleapp.domain.model.BlogItemData
import com.example.sampleapp.domain.model.CafeItemData
import com.example.sampleapp.domain.model.CategoryType
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.model.SortType
import com.example.sampleapp.domain.model.WebItemData
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun getSearchPagingData(search: String): Pager<Int, Search>
    fun getSearchCachingPagingData(search: String): Flow<PagingData<Search>>

    fun getWebSearchPagingData(search: String, sort: SortType, type: CategoryType): Pager<Int, ItemData>

    suspend fun getSearchBlog(search: String, sort: SortType,page: Int, size: Int): SampleResult<List<BlogItemData>>
    suspend fun getSearchCafe(search: String, sort: SortType,page: Int, size: Int): SampleResult<List<CafeItemData>>
    suspend fun getSearchWeb(search: String, sort: SortType,page: Int, size: Int): SampleResult<List<WebItemData>>


    suspend fun getSearchImage(search: String, page: Int, size: Int): SampleResult<List<Search>>
    suspend fun getSearchVideo(search: String, page: Int, size: Int): SampleResult<List<Search>>

}