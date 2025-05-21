package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.StorageRepository
import javax.inject.Inject

class DeleteStorageUseCase @Inject constructor(
    private val repository: StorageRepository
) {
    operator suspend fun invoke(data: Search): SampleResult<Int> {
        return repository.deleteStorage(data)
    }
}