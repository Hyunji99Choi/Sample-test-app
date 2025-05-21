package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.example.sampleapp.domain.model.BlogItemData
import kotlinx.serialization.SerialName
import java.util.Date

@Keep
data class BlogDocument(
    @SerialName("title")
    val title: String,

    @SerialName("contents")
    val contents: String,

    @SerialName("url")
    val url: String,

    @SerialName("blogname")
    val blogName: String,

    @SerialName("thumbnail")
    val thumbnail: String,

    @SerialName("datetime")
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
