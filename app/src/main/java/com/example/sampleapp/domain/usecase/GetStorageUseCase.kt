package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.StorageRepository
import javax.inject.Inject

class GetStorageUseCase @Inject constructor(
    private val repository: StorageRepository
) {
    suspend operator fun invoke(id: String): Search? {
        return repository.getStorage(id)
    }
}