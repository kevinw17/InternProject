package com.example.internproject.network

import com.example.internproject.model.DataResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TwitchService {
    @GET("/helix/streams/")
    fun getStreams(
        @Header("Authorization") authorization : String,
        @Header("Client-ID") clientId : String
    ) : Call<DataResponse>

    @GET("/helix/streams/followed")
    fun getFollowedStreams(
        @Header("Authorization") authorization : String,
        @Header("Client-ID") clientId : String,
        @Query("user_id") userId : String
    ) : Call<DataResponse>

}