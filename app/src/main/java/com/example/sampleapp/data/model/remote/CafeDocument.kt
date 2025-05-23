package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.example.sampleapp.domain.model.CafeItemData
import com.google.gson.annotations.SerializedName
import java.util.Date

@Keep
data class CafeDocument(
    @SerializedName("title")
    val title: String,

    @SerializedName("contents")
    val contents: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("cafename")
    val cafeName: String,

    @SerializedName("thumbnail")
    val thumbnail: String,

    @SerializedName("datetime")
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