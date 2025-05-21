package com.example.sampleapp.domain.usecase

import androidx.paging.PagingData
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStoragePagingUseCase @Inject constructor(
    private val repository: StorageRepository
) {
    operator fun invoke(): Flow<PagingData<Search>> {
        return repository.getStoragePagingData()
    }
}