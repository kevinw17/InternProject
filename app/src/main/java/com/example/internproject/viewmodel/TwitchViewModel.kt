package com.example.internproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.model.Stream
import com.example.internproject.model.Video
import com.example.internproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwitchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _result = MutableStateFlow<List<Stream>?>(null)
    private val _videos = MutableStateFlow<List<Video>?>(null)
    val result: StateFlow<List<Stream>?> = _result
    val videos : StateFlow<List<Video>?> = _videos


    fun getFollowedStreams (authorizationToken : String, clientId : String, userId : String) {
        viewModelScope.launch {
            repository.getFollowedStreams(authorizationToken, clientId, userId).collect { streams ->
                if( streams != null) {
                    _result.value =  streams
                }
            }
        }
    }

    fun getStreams (authorizationToken: String, clientId: String) {
        viewModelScope.launch {
            repository.getStreams(authorizationToken, clientId).collect { streams ->
                if(streams != null) {
                    _result.value =  streams
                }
            }
        }
    }

    fun getVideos (authorizationToken : String, clientId : String, userId : String, type: String) {
        viewModelScope.launch {
            repository.getVideos(authorizationToken, clientId, userId, type).collect { videos ->
                if( videos != null) {
                    _videos.value =  videos
                }
            }
        }
    }

}