package com.example.internproject.repository

import com.example.internproject.model.Stream
import com.example.internproject.model.Video
import com.example.internproject.network.TwitchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(private val apiService : TwitchService) {

//    private val twitchRetrofitInstance = TwitchRetrofitInstance.apiService()
    fun getFollowedStreams(authorizationToken : String, clientId : String, userId : String) : Flow<List<Stream>?> = flow {
        val response = apiService.getFollowedStreams(authorizationToken, clientId, userId).execute()
        if(response.isSuccessful){
            emit(response.body()?.data)
        }
    }.flowOn(Dispatchers.Default)

    fun getStreams(authorizationToken: String, clientId: String) : Flow<List<Stream>?> = flow {
        val response = apiService.getStreams(authorizationToken, clientId).execute()
        if(response.isSuccessful){
            emit(response.body()?.data)
        }
    }.flowOn(Dispatchers.Default)

    fun getVideos(authorizationToken : String, clientId : String, userId : String) : Flow<List<Video>?> = flow {
        val response = apiService.getVideos(authorizationToken, clientId, userId).execute()
        if(response.isSuccessful){
            emit(response.body()?.data)
        }
    }.flowOn(Dispatchers.Default)

}