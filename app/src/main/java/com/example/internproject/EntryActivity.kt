package com.example.internproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.internproject.databinding.ActivityEntryBinding

class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginWithTwitch()
        checkAccessTokenAndLaunchActivity()
    }
    private fun checkAccessTokenAndLaunchActivity() {
        val uri:Uri? = intent.data
        if(uri != null) {
            val accessToken = "Bearer " + (uri.fragment?.split("&")?.map { it.split("=") }?.find { it[0] == "access_token" }?.get(1))

            val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE).edit()
            sharedPreferences.putString(TwitchConstants.TOKEN_ARGUMENT, accessToken)
            sharedPreferences.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginWithTwitch () {
        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val twitchAccessToken = sharedPreferences.getString(TwitchConstants.TOKEN_ARGUMENT, null)

        if (twitchAccessToken != null) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(TwitchConstants.TOKEN_ARGUMENT, twitchAccessToken)
            startActivity(intent)
        } else {
            val clientId = TwitchConstants.CLIENT_ID
            val redirectUri = "https://com.example.internproject"

            val twitchIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(
                    "https://id.twitch.tv/oauth2/authorize?client_id=$clientId&redirect_uri=$redirectUri&response_type=token&scope=user:read:follows")
            )

            binding.btnToMain.setOnClickListener {
                startActivity(twitchIntent)
            }
        }
    }

}