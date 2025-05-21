package com.example.sampleapp.data.model.remote

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class OpenApiResponse<T>(
    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("documents")
    val documents: List<T>
)
