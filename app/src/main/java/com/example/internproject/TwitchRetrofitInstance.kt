package com.example.internproject

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TwitchRetrofitInstance {

    companion object {
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
    }

}