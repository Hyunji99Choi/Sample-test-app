package com.example.sampleapp.domain.model

import java.util.Date

sealed class ItemData {
    abstract val title: String
    abstract val contents: String
    abstract val url: String
    abstract val dateTime: Date
}

// blog
data class BlogItemData(
    override val title: String,
    override val contents: String,
    override val url: String,
    override val dateTime: Date,
    val name: String,
    val thumbnail: String,
) : ItemData()

// cafe
data class CafeItemData(
    override val title: String,
    override val contents: String,
    override val url: String,
    override val dateTime: Date,
    val name: String,
    val thumbnail: String,
) : ItemData()

// web
data class WebItemData(
    override val title: String,
    override val contents: String,
    override val url: String,
    override val dateTime: Date,
) : ItemData()


fun getFakeBlog(): BlogItemData {
    return BlogItemData(
        title = "블로그 제목입니다",
        contents = "블로그 내용이 여기에 들어갑니다.",
        url = "https://example.com",
        dateTime = Date(),
        name = "블로그 작성자",
        thumbnail = "https://example.com/thumbnail.jpg"
    )
}

fun getFakeCafe(): CafeItemData {
    return CafeItemData(
        title = "카페 리뷰입니다.",
        contents = "맛있는 커피와 좋은 분위기에 대해 이야기합니다.",
        url = "https://example.com/cafe",
        dateTime = Date(),
        name = "카페 이름",
        thumbnail = "https://example.com/cafe_thumbnail.jpg"
    )
}

fun getFakeWeb(): WebItemData {
    return WebItemData(
        title = "구글",
        url = "https://www.google.com",
        dateTime = Date(),
        contents = "구글은 세계 최대의 검색 엔진입니다."
    )
}