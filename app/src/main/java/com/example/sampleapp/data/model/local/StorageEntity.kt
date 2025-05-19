package com.example.sampleapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sampleapp.domain.model.DateType
import com.example.sampleapp.domain.model.Search
import java.util.Date

@Entity(tableName = "storage_table")
data class StorageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "type")
    val type: DateType,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "imagePath")
    val imagePath: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "collection")
    val collection: String? = null
)

fun StorageEntity.toDomain() = Search(
    type = type,
    title = title,
    imagePath = imagePath,
    url = url,
    date = date,
    collection = collection,
    savedState = true
)

fun Search.toEntity() = StorageEntity(
    id = getKey(),
    type = type,
    title = title,
    imagePath = imagePath,
    url = url,
    date = date,
    collection = collection
)