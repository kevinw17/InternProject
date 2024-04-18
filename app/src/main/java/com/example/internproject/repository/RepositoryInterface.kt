package com.example.internproject.repository

import com.example.internproject.model.Stream
import com.example.internproject.model.Video
import kotlinx.coroutines.flow.Flow

interface RepositoryInterface {

    fun getFollowedStreams(authorizationToken : String, clientId : String, userId : String) : Flow<List<Stream>?>

    fun getStreams(authorizationToken: String, clientId: String) : Flow<List<Stream>?>

    fun getVideos(authorizationToken : String, clientId : String, userId : String, type : String) : Flow<List<Video>?>

}