package com.example.sampleapp.data.network

import com.example.sampleapp.data.model.remote.ImageDocument
import com.example.sampleapp.data.model.remote.OpenApiResponse
import com.example.sampleapp.data.model.remote.VideoDocument
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SampleService {
    @GET("v2/search/image")
    suspend fun getImage(
        @Query("query") search: String,
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("sort") sort: String = "recency",
    ): Response<OpenApiResponse<ImageDocument>>

    @GET("v2/search/vclip")
    suspend fun getVideo(
        @Query("query") search: String,
        @Query("page") page: Int?,
        @Query("size") size: Int?,
        @Query("sort") sort: String = "recency",
    ): Response<OpenApiResponse<VideoDocument>>
}