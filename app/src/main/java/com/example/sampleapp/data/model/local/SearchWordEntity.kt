package com.example.sampleapp.data.model.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_word_table")
data class SearchWordEntity(
    @PrimaryKey
    @ColumnInfo(name = "keyword")
    val keyword: String,

    @ColumnInfo(name = "current_page")
    var currentPage: Int,

    @ColumnInfo(name = "last_updated")
    val lastUpdated: Long
)
