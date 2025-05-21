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