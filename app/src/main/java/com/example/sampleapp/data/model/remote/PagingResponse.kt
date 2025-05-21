package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PagingResponse<T>(
    @SerializedName("data")
    val data: List<T>,

    @SerializedName("pagination")
    val pagination: PaginationDTO = PaginationDTO()
)
