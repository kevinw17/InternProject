package com.example.internproject

import com.google.gson.annotations.SerializedName

data class User (
    val id : String,
    val login : String,
    @SerializedName("display_name")
    val displayName : String,
    val type : String,
    @SerializedName("broadcaster_type")
    val broadcasterType : String,
    val description : String,
    @SerializedName("profile_image_url")
    val profileImageUrl : String,
    @SerializedName("offline_image_url")
    val offlineImageUrl : String,
    @SerializedName("view_count")
    val viewCount : Int,
    val email : String,
    @SerializedName("created_at")
    val createdAt : String,
)