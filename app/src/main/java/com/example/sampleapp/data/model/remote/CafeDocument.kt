package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.example.sampleapp.domain.model.CafeItemData
import kotlinx.serialization.SerialName
import java.util.Date

@Keep
data class CafeDocument(
    @SerialName("title")
    val title: String,

    @SerialName("contents")
    val contents: String,

    @SerialName("url")
    val url: String,

    @SerialName("cafename")
    val cafeName: String,

    @SerialName("thumbnail")
    val thumbnail: String,

    @SerialName("datetime")
    val dateTime: Date
)

fun CafeDocument.toDomain() = CafeItemData(
    title = title,
    contents = contents,
    url = url,
    dateTime = dateTime,
    name = cafeName,
    thumbnail = thumbnail
)