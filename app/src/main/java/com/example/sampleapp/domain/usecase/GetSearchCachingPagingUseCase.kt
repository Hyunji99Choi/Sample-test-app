package com.example.sampleapp.domain.usecase

import androidx.paging.PagingData
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchCachingPagingUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(search: String): Flow<PagingData<Search>> {
        return repository.getSearchCachingPagingData(search)
    }
}