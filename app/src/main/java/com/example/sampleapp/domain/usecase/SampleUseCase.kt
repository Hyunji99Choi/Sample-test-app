package com.example.sampleapp.domain.usecase

import com.example.sampleapp.domain.repository.SampleRepository
import javax.inject.Inject

class SampleUseCase @Inject constructor(
    private val repository: SampleRepository
) {
    operator fun invoke() {

    }
}