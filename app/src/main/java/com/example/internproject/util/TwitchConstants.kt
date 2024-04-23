package com.example.internproject.util

import android.net.Uri
import com.example.internproject.BuildConfig

object TwitchConstants {
    const val CLIENT_ID = BuildConfig.CLIENT_ID
    const val DUMMY_USER_ID = "654851614"
    const val TOKEN_ARGUMENT = "twitch_access_token"
    const val IMAGE_WIDTH = 1280
    const val IMAGE_HEIGHT = 960
    const val VIDEO_USER_ID = "70225218"
    const val VIDEO_TYPE = "archive"
    const val REDIRECT_URI = "https://com.example.internproject"
    val TWITCH_AUTH_URL = String.format("https://id.twitch.tv/oauth2/authorize?client_id=%s&redirect_uri=%s&response_type=token&scope=user:read:follows", CLIENT_ID, REDIRECT_URI)
    fun extractAccessToken(uri: Uri?): String? {
        return uri?.fragment?.split("&")?.map { it.split("=") }
            ?.find { it[0] == "access_token" }?.get(1)
    }
}