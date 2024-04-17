package com.example.internproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.model.Stream
import com.example.internproject.model.Video
import com.example.internproject.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwitchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _followedStreams = MutableSharedFlow<List<Stream>?>()
    private val _videos = MutableSharedFlow<List<Video>?>()
    private val _streams = MutableStateFlow<List<Stream>?>(null)
    val followedStreams = _followedStreams.asSharedFlow()
    val videos = _videos.asSharedFlow()
    val streams = _streams.asStateFlow()


    fun getFollowedStreams (authorizationToken : String, clientId : String, userId : String) {
        viewModelScope.launch {
            repository.getFollowedStreams(authorizationToken, clientId, userId).collect { streams ->
                if( streams != null) {
                    _followedStreams.emit(streams)
                }
            }
        }
    }

    fun getStreams (authorizationToken: String, clientId: String) {
        viewModelScope.launch {
            repository.getStreams(authorizationToken, clientId).collect { streams ->
                if(streams != null) {
                    _streams.value = streams
                }
            }
        }
    }

    fun getVideos (authorizationToken : String, clientId : String, userId : String, type: String) {
        viewModelScope.launch {
            repository.getVideos(authorizationToken, clientId, userId, type).collect { videos ->
                if( videos != null) {
                    _videos.emit(videos)
                }
            }
        }
    }

}