package com.example.sampleapp.presentation.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.usecase.GetPagingDataUseCase
import com.example.sampleapp.domain.usecase.GetSearchImagesUseCase
import com.example.sampleapp.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val getSearchImagesUseCase: GetSearchImagesUseCase,
    private val getPagingDataUseCase: GetPagingDataUseCase
) : BaseViewModel() {

    private val _searchResult = MutableLiveData<List<Search>>()
    val searchResult: LiveData<List<Search>>
        get() = _searchResult


    fun getSearchImages(search: String) {
        viewModelScope.launch {
            when (val result = getSearchImagesUseCase(search = search, page = 0, size = 10)) {
                is SampleResult.Success -> {
                    _searchResult.value = result.data
                }
                else -> {
                    setError(result = result)
                }
            }

        }
    }

}