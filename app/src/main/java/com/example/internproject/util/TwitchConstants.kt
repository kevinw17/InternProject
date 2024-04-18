package com.example.internproject.util

import android.net.Uri
import com.example.internproject.BuildConfig

object TwitchConstants {
    val CLIENT_ID = BuildConfig.CLIENT_ID
    val DUMMY_USER_ID = "654851614"
    val TOKEN_ARGUMENT = "twitch_access_token"
    val IMAGE_WIDTH = 1280
    val IMAGE_HEIGHT = 960
    val VIDEO_USER_ID = "70225218"
    val VIDEO_TYPE = "archive"
    val REDIRECT_URI = "https://com.example.internproject"
    val TWITCH_AUTH_URL = String.format("https://id.twitch.tv/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=token&scope=user:read:follows", CLIENT_ID, REDIRECT_URI)
    fun extractAccessToken(uri: Uri?): String? {
        return uri?.fragment?.split("&")?.map { it.split("=") }
            ?.find { it[0] == "access_token" }?.get(1)
    }
}