package com.example.sampleapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.sampleapp.data.model.local.RemoteKey
import com.example.sampleapp.data.model.local.SearchEntity
import com.example.sampleapp.data.model.local.SearchWordEntity

@Database(entities = [SearchEntity::class, SearchWordEntity::class, RemoteKey::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class CachingDatabase : RoomDatabase() {
    abstract fun SearchListDao(): SearchListDao
    abstract fun SearchWordDao(): SearchWordDao


    abstract fun remoteKeyDao(): RemoteKeyDao

}