package com.example.sampleapp.domain.model

import java.util.Date


enum class DateType {
    IMAGE, VIDEO
}

data class Search(
    val type: DateType,

    val title: String,

    val imagePath: String,

    val url: String,

    val date: Date,

    val collection: String? = null,


    var savedState: Boolean = false,

    ) {

    fun getKey(): String {
        return "${type}${imagePath}${title}${date}"
    }
}