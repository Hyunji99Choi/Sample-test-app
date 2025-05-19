package com.example.sampleapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class PaginationDTO(
    @SerializedName("page")
    val page: Int? = null,

    @SerializedName("count")
    val count: Int? = null,

    @SerializedName("totalElements")
    val totalElements: Long? = null,

    @SerializedName("totalPages")
    val totalPages: Int? = null,

    @SerializedName("size")
    val size: Int? = null
)
