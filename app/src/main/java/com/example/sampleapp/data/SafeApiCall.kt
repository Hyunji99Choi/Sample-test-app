package com.example.sampleapp.data

import com.example.sampleapp.domain.model.SampleError
import com.example.sampleapp.domain.model.SampleResult
import retrofit2.Response
import java.io.IOException


suspend fun <T : Any, R : Any> safeApiCall(
    call: suspend () -> Response<T>,
    transform: ((T) -> R)
): SampleResult<R> {
    return try {
        val myResp = call.invoke()

        if (myResp.isSuccessful) {
            myResp.body()?.let {
                SampleResult.Success(transform(it))
            } ?: SampleResult.Failure(
                SampleError.ApiError(myResp.code(), "Empty response body")
            )
        } else {
            SampleResult.Failure(SampleError.ApiError(myResp.code(), myResp.message()))
        }
    } catch (e: IOException) {
        SampleResult.Failure(
            SampleError.NetworkError("인터넷 연결 실패", e.hashCode())
        )
    } catch (e: Exception) {
        SampleResult.Failure(
            SampleError.UnknownError(e)
        )
    }
}

/*private fun getErrorResponse(errorBody: ResponseBody): ErrorResponse? {
    return  NetworkModule.provideRetrofit().responseBodyConverter<ErrorResponse>(
        ErrorResponse::class.java,
        ErrorResponse::class.java.annotations
    ).convert(errorBody)
}*/

/*
fun convertErrorBody(it: ResponseBody): ErrorResponse? {
    return try {
        val gson = Gson()
        val type = object : TypeToken<ErrorResponse>() {}.type
        gson.fromJson(it.charStream(), type)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}*/
