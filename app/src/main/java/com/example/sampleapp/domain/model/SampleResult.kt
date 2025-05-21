package com.example.sampleapp.domain.model

sealed class SampleResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : SampleResult<T>()
    data class Failure(val error: SampleError) : SampleResult<Nothing>()
}
// TODO Git add 하기..
sealed class SampleError {
    data class NetworkError(val message: String, val code: Int? = null) : SampleError()
    data class ApiError(val code: Int, val message: String) : SampleError()
    data class DatabaseError(val reason: String) : SampleError()
    data class UnknownError(val exception: Throwable) : SampleError()

    fun getErrorMessage(): String {
        return when (this) {
            is NetworkError -> "네트워크 오류: $message"
            is ApiError -> "API 오류: $message (코드: $code)"
            is DatabaseError -> "데이터베이스 오류: $reason"
            is UnknownError -> "알 수 없는 오류: ${exception.localizedMessage}"
            else -> "알 수 없는 오류 반환 타입"
        }
    }
}

fun <T: Any> SampleResult<T>.getOrNull(): T? =
    (this as? SampleResult.Success)?.data