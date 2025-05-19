package com.example.sampleapp.presentation.ui.search

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.usecase.GetSearchCachingPagingUseCase
import com.example.sampleapp.domain.usecase.GetSearchPagingUseCase
import com.example.sampleapp.domain.usecase.GetStorageIdFlowUseCase
import com.example.sampleapp.domain.usecase.GetStorageUseCase
import com.example.sampleapp.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchPagingUseCase: GetSearchPagingUseCase,
    private val getSearchCachingPagingUseCase: GetSearchCachingPagingUseCase,
    private val getStorageIdFlowUseCase: GetStorageIdFlowUseCase,
) : BaseViewModel() {

    private val searchWord = MutableStateFlow<String>("")

    private val storageId = getStorageIdFlowUseCase()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchPagingData = searchWord
        .debounce(250)
        .distinctUntilChanged()
        .map { word ->
            word.ifEmpty {
                "fromm" // default search word
            }
        }
        .flatMapLatest {
            getSearchCachingPagingUseCase(it)
        }
        .cachedIn(viewModelScope)


    val searchList = searchPagingData.combine(storageId) { paging, ids ->
            paging.map { item ->
                item.apply { savedState = ids.contains(item.getKey()) }
            }
        }

    private var moveTopState: Boolean = false

    fun search(word: String) {
        moveTopState = true
        searchWord.value = word
    }

    fun getTopState(): Boolean {
        return moveTopState.also { moveTopState = false }
    }
}