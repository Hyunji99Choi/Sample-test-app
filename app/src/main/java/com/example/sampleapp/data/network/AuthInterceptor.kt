package com.example.sampleapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val authorization: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", authorization)
            .build()
        return chain.proceed(request)
    }
}