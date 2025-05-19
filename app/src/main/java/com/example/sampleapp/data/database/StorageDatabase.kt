package com.example.sampleapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sampleapp.data.model.local.StorageEntity

@Database(entities = [StorageEntity::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class StorageDatabase : RoomDatabase() {
    abstract fun storageDataDao(): StorageDataDao
}
