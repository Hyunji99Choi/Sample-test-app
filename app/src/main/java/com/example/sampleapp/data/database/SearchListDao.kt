package com.example.sampleapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.data.model.local.SearchEntity

@Dao
interface SearchListDao {


    @Query("SELECT * FROM search_caching_table WHERE keyword = :keyword ORDER BY date DESC LIMIT :pageSize OFFSET :offset")
    suspend fun getCachedPage(
        keyword: String,
        pageSize: Int,
        offset: Int
    ): List<SearchEntity>



    @Query("SELECT * FROM search_caching_table WHERE keyword = :keyword ORDER BY date DESC")
    fun pagingSource(keyword: String): PagingSource<Int, SearchEntity>

    @Query("DELETE FROM search_caching_table WHERE keyword = :keyword")
    suspend fun clearByKeyword(keyword: String)

    @Query("DELETE FROM search_caching_table WHERE updated_at < :expiryTime")
    suspend fun deleteExpiredEntries(expiryTime: Long)




    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(data: List<SearchEntity>)

    @Query("SELECT * FROM search_caching_table LIMIT :size OFFSET :page * :size")
    fun getPaging(
        page: Int,
        size: Int
    ): List<SearchEntity> //WHERE entity = :type


    @Query("SELECT * FROM search_caching_table")
    fun pagingSource(): PagingSource<Int, SearchEntity>

    @Query("DELETE FROM search_caching_table")
    suspend fun clearAll()

}