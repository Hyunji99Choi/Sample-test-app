package com.example.sampleapp.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BasePagingDataSource<T: Any> (

): PagingSource<Int, T>() {
    companion object {
        const val pagingSize = 10
    }

    open protected val startKey = 0

    private fun ensureValidKey(key: Int) = Integer.max(startKey, key)

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val position = params.key ?: startKey

        return try {
            withContext(Dispatchers.IO) {
                val response = getPagingData(position, params.loadSize)
                val responseData = response


                LoadResult.Page(
                    data = responseData,
                    prevKey = if (position == startKey) null else position - 1,
                    nextKey = if (responseData.isEmpty()) null else position + 1
                )
            }


        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

    abstract suspend fun getPagingData(position: Int, size: Int): List<T>
}