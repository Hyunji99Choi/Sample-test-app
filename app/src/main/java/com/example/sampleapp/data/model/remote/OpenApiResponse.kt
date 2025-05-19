package com.example.sampleapp.data.model.remote

import com.google.gson.annotations.SerializedName

data class OpenApiResponse<T>(
    @SerializedName("meta")
    val meta: Meta,

    @SerializedName("documents")
    val documents: List<T>
)
