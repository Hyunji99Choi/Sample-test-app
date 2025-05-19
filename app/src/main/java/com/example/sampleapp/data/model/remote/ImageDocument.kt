package com.example.sampleapp.data.model.remote

import com.example.sampleapp.domain.model.DateType
import com.example.sampleapp.domain.model.Search
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ImageDocument(
    @SerializedName("collection")
    val collection: String?,          // 컬렉션

    @SerializedName("thumbnail_url")
    val thumbnailUrl: String?,        // 미리보기 이미지 URL

    @SerializedName("image_url")
    val imageUrl: String?,            // 이미지 URL

    @SerializedName("width")
    val width: Int?,                  // 이미지의 가로 길이

    @SerializedName("height")
    val height: Int?,                 // 이미지의 세로 길이

    @SerializedName("display_sitename")
    val displaySitename: String?,     // 출처

    @SerializedName("doc_url")
    val docUrl: String?,             // 문서 URL

    @SerializedName("datetime")
    val datetime: Date?     // 문서 작성시간, [YYYY]-[MM]-[DD]T[hh]:[mm]:[ss].000+[tz]
)


fun ImageDocument.toDomain() = Search(
    type = DateType.IMAGE,
    title = displaySitename ?: "",
    imagePath = thumbnailUrl ?: "",
    url = docUrl ?: "",
    date = datetime ?: Date(),
    collection = collection
)