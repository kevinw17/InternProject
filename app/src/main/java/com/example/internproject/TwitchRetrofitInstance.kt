package com.example.internproject

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TwitchRetrofitInstance {

    companion object {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.twitch.tv/helix/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun apiService() : TwitchService {
            return  retrofit.create(TwitchService::class.java)
        }
    }

}