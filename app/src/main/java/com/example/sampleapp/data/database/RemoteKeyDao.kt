package com.example.sampleapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampleapp.data.model.local.RemoteKey

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(remoteKey: RemoteKey)

    @Query("SELECT * FROM remote_keys WHERE entity = :type")
    suspend fun remoteKeyByQuery(type: String): RemoteKey

    @Query("DELETE FROM remote_keys WHERE entity = :type")
    suspend fun deleteByQuery(type: String)
}