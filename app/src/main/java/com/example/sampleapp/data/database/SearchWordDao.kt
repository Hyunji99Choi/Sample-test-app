package com.example.sampleapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.sampleapp.data.model.local.SearchWordEntity

@Dao
interface SearchWordDao {

    @Query("SELECT * FROM search_word_table WHERE keyword = :keyword")
    suspend fun getSavedWord(keyword: String): SearchWordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(info: SearchWordEntity)



    @Query("DELETE FROM search_word_table WHERE last_updated < :expiryTime")
    suspend fun deleteExpiredInfo(expiryTime: Long)

}