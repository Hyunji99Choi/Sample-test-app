package com.example.sampleapp.di

import com.example.sampleapp.BuildConfig
import com.example.sampleapp.data.network.AuthInterceptor
import com.example.sampleapp.data.network.SampleService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor("KakaoAK $REST_API_KEY")
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            client.addInterceptor(logger)
        }

        return client.build()
    }


    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun getService(retrofit: Retrofit): SampleService = retrofit.create(SampleService::class.java)

    companion object {
        const val BASE_URL = "https://dapi.kakao.com/"
        val REST_API_KEY = BuildConfig.SAMPLE_OPEN_API_KEY
    }
}