package com.example.sampleapp.data.model.local

data class MemoryCachedData<T>(
    val data: T,
    val timestamp: Long
)
