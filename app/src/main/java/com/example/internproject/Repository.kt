package com.example.internproject

import android.util.Log
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository () {

    private val twitchRetrofitInstance = TwitchRetrofitInstance.apiService()

    fun getFollowedStreams(authorizationToken : String, clientId : String, userId : String) : Flow<List<Stream>?> = flow {
        val response = twitchRetrofitInstance.getFollowedStreams(authorizationToken, clientId, userId).execute()
        if(response.isSuccessful){
            emit(response.body()?.data)
        }
    }.flowOn(Dispatchers.Default)

    fun getStreams(authorizationToken: String, clientId: String) : Flow<List<Stream>?> = flow {
        val response = twitchRetrofitInstance.getStreams(authorizationToken, clientId).execute()
        if(response.isSuccessful){
            emit(response.body()?.data)
        }
    }.flowOn(Dispatchers.Default)

}