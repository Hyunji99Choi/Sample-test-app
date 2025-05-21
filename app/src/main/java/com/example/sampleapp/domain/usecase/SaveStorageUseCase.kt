package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.StorageRepository
import javax.inject.Inject

class SaveStorageUseCase @Inject constructor(
    private val repository: StorageRepository
) {
    operator suspend fun invoke(data: Search): SampleResult<Long> {
        return repository.insertStorage(data)
    }
}