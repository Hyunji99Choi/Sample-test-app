package com.example.sampleapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class PagingResponse<T>(
    @SerializedName("data")
    val data: List<T>,

    @SerializedName("pagination")
    val pagination: PaginationDTO = PaginationDTO()
)
