package com.example.sampleapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.sampleapp.domain.model.DateType
import com.example.sampleapp.domain.model.Search
import java.util.Date

@Entity(tableName = "search_caching_table")
data class SearchEntity(

    @ColumnInfo(name = "keyword")
    val keyword: String,

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "type")
    val type: DateType,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "image_path")
    val imagePath: String,

    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "collection")
    val collection: String? = null,


    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)

fun SearchEntity.toDomain(): Search {
    return Search(
        type = type,
        title = title,
        imagePath = imagePath,
        url = url,
        date = date,
        collection = collection
    )
}

fun Search.toEntity(keyword: String, time: Long): SearchEntity {
    return SearchEntity(
        keyword = keyword,
        id = getKey(),
        type = type,
        title = title,
        imagePath = imagePath,
        url = url,
        date = date,
        collection = collection,
        updatedAt = time
    )
}
