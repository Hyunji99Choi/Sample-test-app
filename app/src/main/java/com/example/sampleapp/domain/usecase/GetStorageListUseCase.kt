package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.SampleRepository
import com.example.sampleapp.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStorageListUseCase @Inject constructor(
    private val repository: StorageRepository
) {
    operator fun invoke(): Flow<List<Search>> {
        return repository.getStorageFlowData()
    }
}