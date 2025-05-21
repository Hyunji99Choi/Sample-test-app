package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Meta(
    @SerializedName("total_count")
    val totalCount: Int,

    @SerializedName("pageable_count")
    val pageableCount: Int,

    @SerializedName("is_end")
    val isEnd: Boolean
)
