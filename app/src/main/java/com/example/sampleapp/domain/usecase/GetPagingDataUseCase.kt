package com.example.sampleapp.domain.usecase

import androidx.paging.PagingData
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.SampleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagingDataUseCase @Inject constructor(
    private val repository: SampleRepository
) {
    operator fun invoke(): Flow<PagingData<Search>> {
        return repository.getPagingData().flow
    }
}