package com.krutika.bookmyshow.data.module

import com.krutika.bookmyshow.data.remote.NetworkConnectionInterceptor
import com.speedyy.rider.data.remote.service.ShowtimeService
import com.speedyy.rider.utils.retrofit.FlowCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    @Singleton
    @Provides
    fun provideHttpClient(
        apiExceptionInterceptor: NetworkConnectionInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiExceptionInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://demo2782755.mockable.io/")
            .client(okHttpClient)
            .addCallAdapterFactory(FlowCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())

            .build()
    }


    @Singleton
    @Provides
    fun provideShowTimeService(
        retrofit: Retrofit,
    ): ShowtimeService {
        return retrofit.create(ShowtimeService::class.java)
    }


}
