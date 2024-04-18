package com.example.internproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internproject.model.Stream
import com.example.internproject.model.Video
import com.example.internproject.repository.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TwitchViewModel @Inject constructor(private val repository: RepositoryInterface) : ViewModel() {

    private val _followedStreams = MutableSharedFlow<List<Stream>?>()
    private val _videos = MutableSharedFlow<List<Video>?>()
    private val _streams = MutableSharedFlow<List<Stream>?>()
    private val _isLoading = MutableStateFlow(false)
    val followedStreams = _followedStreams.asSharedFlow()
    val videos = _videos.asSharedFlow()
    val streams = _streams.asSharedFlow()
    val isLoading = _isLoading.asStateFlow()


    fun getFollowedStreams (authorizationToken : String, clientId : String, userId : String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getFollowedStreams(authorizationToken, clientId, userId).collect { streams ->
                _isLoading.value = false
                if( streams != null) {
                    _followedStreams.emit(streams)
                }
            }
        }
    }

    fun getStreams (authorizationToken: String, clientId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getStreams(authorizationToken, clientId).collect { streams ->
                _isLoading.value = false
                if(streams != null) {
                    _streams.emit(streams)
                }
            }
        }
    }

    fun getVideos (authorizationToken : String, clientId : String, userId : String, type: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getVideos(authorizationToken, clientId, userId, type).collect { videos ->
                _isLoading.value = false
                if( videos != null) {
                    _videos.emit(videos)
                }
            }
        }
    }

}