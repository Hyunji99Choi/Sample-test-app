package com.example.sampleapp.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sampleapp.domain.model.SampleResult
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel: ViewModel() {

    private val defaultError = "ERROR"

    private val _errorFlow = MutableSharedFlow<Pair<String, String>>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val error: SharedFlow<Pair<String, String>> = _errorFlow.asSharedFlow()


    open fun setError(result: SampleResult<*>, code: String = defaultError) {
        viewModelScope.launch {
            when (result) {

                is SampleResult.Failure -> {
                    _errorFlow.emit(
                        code to result.error.getErrorMessage()
                    )
                }

                else -> {}
            }
        }
    }

}