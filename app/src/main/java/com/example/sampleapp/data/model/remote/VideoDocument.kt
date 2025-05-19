package com.example.sampleapp.data.model.remote

import com.example.sampleapp.domain.model.DateType
import com.example.sampleapp.domain.model.Search
import com.google.gson.annotations.SerializedName
import java.util.Date

data class VideoDocument(
    @SerializedName("title")
    val title: String,            // 동영상 제목

    @SerializedName("url")
    val url: String,              // 동영상 링크

    @SerializedName("datetime")
    val datetime: Date,         // 동영상 등록일, ISO 8601

    @SerializedName("play_time")
    val playTime: Int,            // 동영상 재생시간, 초 단위

    @SerializedName("thumbnail")
    val thumbnail: String,        // 동영상 미리보기 URL

    @SerializedName("author")
    val author: String            // 동영상 업로더
)

fun VideoDocument.toDomain() = Search(
    type = DateType.VIDEO,
    title = title,
    imagePath = thumbnail,
    url = url,
    date = datetime,
    collection = null
)