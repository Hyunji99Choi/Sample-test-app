package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.example.sampleapp.domain.model.WebItemData
import com.google.gson.annotations.SerializedName
import java.util.Date


@Keep
data class WebDocument(
    @SerializedName("title")
    val title: String,

    @SerializedName("contents")
    val contents: String,

    @SerializedName("url")
    val url: String,

    @SerializedName("datetime")
    val dateTime: Date
)

fun WebDocument.toDomain() = WebItemData(
    title = title,
    contents = contents,
    url = url,
    dateTime = dateTime
)