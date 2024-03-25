package com.example.internproject

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TwitchService {

    @GET("/users")
    fun getUsers(
        @Header("Authorization") authorization : String,
        @Header("Client-ID") clientId : String,
        @Query("id") id : String
    ) : Call<Users>

}