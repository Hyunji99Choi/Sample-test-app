package com.example.sampleapp.domain.usecase

import androidx.paging.PagingData
import com.example.sampleapp.domain.model.CategoryType
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWebSearchPagingUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(search: String, type: CategoryType): Flow<PagingData<ItemData>> {
        return repository.getWebSearchPagingData(search, type).flow
    }
}