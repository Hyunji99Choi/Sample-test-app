package com.example.sampleapp.presentation.ui.storage

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.sampleapp.domain.model.SampleResult
import com.example.sampleapp.domain.model.Search
import com.example.sampleapp.domain.usecase.DeleteStorageUseCase
import com.example.sampleapp.domain.usecase.GetSearchPagingUseCase
import com.example.sampleapp.domain.usecase.GetStorageListUseCase
import com.example.sampleapp.domain.usecase.GetStoragePagingUseCase
import com.example.sampleapp.domain.usecase.SaveStorageUseCase
import com.example.sampleapp.presentation.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val getStoragePagingUseCase: GetStoragePagingUseCase,
    private val saveStorageUseCase: SaveStorageUseCase,
    private val deleteStorageUseCase: DeleteStorageUseCase,
) : BaseViewModel() {

    val storagePagingData = getStoragePagingUseCase().cachedIn(viewModelScope)

    fun savedStorage(data: Search) {
        viewModelScope.launch {
            when (val result = saveStorageUseCase.invoke(data)) {
                is SampleResult.Success -> {
                    Log.d("StorageViewModel", "savedStorage: ${result.data}")
                }

                else -> {
                    setError(result)
                }
            }
        }
    }

    fun deleteStorage(data: Search) {
        viewModelScope.launch {
            when (val result = deleteStorageUseCase.invoke(data)) {
                is SampleResult.Success -> {
                    Log.d("StorageViewModel", "deleteStorage: ${result.data}")
                }

                else -> {
                    setError(result)
                }
            }
        }
    }

}