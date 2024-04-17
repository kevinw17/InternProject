package com.example.internproject.util

import android.content.Context

object TwitchSharedPreferences {
    fun setAccessToken(context: Context, accessToken: String) {
        val sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        return with(sharedPreferences.edit()) {
            putString(TwitchConstants.TOKEN_ARGUMENT, accessToken)
            apply()
        }
    }

    fun getAccessToken(context: Context): String? {
        val accessToken = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE).getString(TwitchConstants.TOKEN_ARGUMENT, null)

        return accessToken
    }
}