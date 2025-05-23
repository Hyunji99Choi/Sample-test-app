package com.example.sampleapp.presentation.ui.website

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.sampleapp.domain.model.CategoryType
import com.example.sampleapp.domain.model.ItemData
import com.example.sampleapp.domain.model.SortType
import com.example.sampleapp.domain.usecase.GetWebSearchAllUseCase
import com.example.sampleapp.domain.usecase.GetWebSearchPagingUseCase
import com.example.sampleapp.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class WebsiteViewModel @Inject constructor(
    private val getWebSearchPagingUseCase: GetWebSearchPagingUseCase,
    private val getWebSearchAllUseCase: GetWebSearchAllUseCase
) : BaseViewModel() {

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    private val _selectedSort = MutableStateFlow(SortType.ACCURACY)
    val selectedSort: StateFlow<SortType> = _selectedSort.asStateFlow()

    private val _selectedCategory = MutableStateFlow(CategoryType.ALL)
    val selectedCategory: StateFlow<CategoryType> = _selectedCategory.asStateFlow()

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun updateSort(sort: SortType) {
        _selectedSort.value = sort
    }

    fun updateCategory(category: CategoryType) {
        _selectedCategory.value = category
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchPaging: Flow<PagingData<ItemData>> = combine(
        query,
        selectedSort,
        selectedCategory
    ) { query, sort, category ->
        Triple(query, sort, category)
    }.distinctUntilChanged()
        .flatMapLatest { (query, sort, category) ->
            if (query.isBlank()) {
                flowOf(PagingData.empty())
            } else {
                getWebSearchPagingUseCase(
                    search = query,
                    sort = sort,
                    type = category
                )
            }
        }
        .cachedIn(viewModelScope)


    val mainList: Flow<List<List<ItemData>>> = combine(query, selectedSort, selectedCategory) {
            query, sort, category->
        if (query.isBlank() || category != CategoryType.ALL) {
            emptyList()
        } else {
            getWebSearchAllUseCase(
                search = query,
                sort = sort
            ) { err ->
                setError(err)
            }
        }
    }

}