package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.example.sampleapp.domain.model.WebItemData
import kotlinx.serialization.SerialName
import java.util.Date


@Keep
data class WebDocument(
    @SerialName("title")
    val title: String,

    @SerialName("contents")
    val contents: String,

    @SerialName("url")
    val url: String,

    @SerialName("datetime")
    val dateTime: Date
)

fun WebDocument.toDomain() = WebItemData(
    title = title,
    contents = contents,
    url = url,
    dateTime = dateTime
)