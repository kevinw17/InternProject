package com.example.internproject.dagger

import com.example.internproject.network.TwitchService
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
object TwitchApiModule {

    @Provides
    @Singleton
    fun provideApiService() : TwitchService {
        val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        fun apiService() : TwitchService {
            return  retrofit.create(TwitchService::class.java)
        }

        return apiService()
    }

}