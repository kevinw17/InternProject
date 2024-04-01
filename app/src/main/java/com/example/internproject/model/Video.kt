package com.example.internproject.model

import com.google.gson.annotations.SerializedName

data class Video(
    val id: String,
    @SerializedName("stream_id")
    val streamId: String?,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("user_login")
    val userLogin: String,
    @SerializedName("user_name")
    val userName: String,
    val title: String,
    val description: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("published_at")
    val publishedAt: String,
    val url: String,
    @SerializedName("thumbnail_url")
    val thumbnailUrl: String,
    val viewable: String,
    @SerializedName("view_count")
    val viewCount: Int,
    val language: String,
    val type: String,
    val duration: String,
    @SerializedName("muted_segments")
    val mutedSegments: List<MutedSegment>?
)
