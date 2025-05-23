package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.example.sampleapp.domain.model.BlogItemData
import com.google.gson.annotations.SerializedName
import java.util.Date

@Keep
data class BlogDocument(
    @SerializedName("title")
    val title: String,

    @SerializedName("contents")
    val contents: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("blogname")
    val blogName: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("datetime")
    val dateTime: Date
)

fun BlogDocument.toDomain() = BlogItemData(
    title = title,
    contents = contents,
    url = url,
    dateTime = dateTime,
    name = blogName,
    thumbnail = thumbnail
)
