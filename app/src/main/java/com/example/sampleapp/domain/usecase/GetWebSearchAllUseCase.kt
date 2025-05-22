package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.SortType
import com.example.sampleapp.domain.repository.SearchRepository
import javax.inject.Inject

class GetWebSearchAllUseCase @Inject constructor(
    private val repository: SearchRepository
) {

    private val startPage = 1
    private val pageSize = 3


    suspend operator fun invoke(
        search: String,
        sort: SortType
        // TODO error... function
    ): List<List<ItemData>> {
        val blog = repository.getSearchBlog(search, sort, startPage, pageSize)
        val cafe = repository.getSearchCafe(search, sort, startPage, pageSize)
        val web = repository.getSearchWeb(search, sort, startPage, pageSize)

        return mutableListOf<List<ItemData>>().apply {

            when (blog) {
                is SampleResult.Success -> {
                    add(blog.data)
                }

                is SampleResult.Failure -> {
                    // TODO error
                }
            }

            when (cafe) {
                is SampleResult.Success -> {
                    add(cafe.data)
                }

                is SampleResult.Failure -> {
                    // TODO error
                }
            }

            when (web) {
                is SampleResult.Success -> {
                    add(web.data)
                }

                is SampleResult.Failure -> {
                    // TODO error
                }
            }
        }
    }

}
