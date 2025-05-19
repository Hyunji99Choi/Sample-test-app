package com.example.sampleapp.data.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.data.model.local.StorageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDataDao {

    @Query("SELECT * FROM storage_table LIMIT :size OFFSET :page * :size")
    fun getStoragePaging(
        page: Int,
        size: Int
    ): List<StorageEntity>


    @Query("SELECT * FROM storage_table")
    fun getStoragePaging(): PagingSource<Int, StorageEntity>


    @Query("SELECT * FROM storage_table")
    fun getStorageFlow(): Flow<List<StorageEntity>>

    @Query("SELECT id FROM storage_table ")
    fun getStorageIdFlow(): Flow<List<String>>

    @Query("SELECT * FROM storage_table WHERE id = :id")
    fun getStorage(id: String): StorageEntity?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStorage(data: StorageEntity): Long

    @Query("DELETE FROM storage_table WHERE id = :id")
    fun deleteStorage(id: String): Int
}