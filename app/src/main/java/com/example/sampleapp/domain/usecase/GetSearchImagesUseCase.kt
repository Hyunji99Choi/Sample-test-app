package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.repository.SampleRepository
import com.example.sampleapp.domain.repository.SearchRepository
import javax.inject.Inject

class GetSearchImagesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(
        search: String,
        page: Int,
        size: Int
    ): SampleResult<List<Search>> {
        return repository.getSearchImage(
            search = search, page = page, size = size
        )
    }
}