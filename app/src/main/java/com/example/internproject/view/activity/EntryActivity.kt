package com.example.internproject.view.activity


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.internproject.databinding.ActivityEntryBinding
import com.example.internproject.util.TwitchConstants
import com.example.internproject.util.TwitchSharedPreferences
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
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
            val accessToken = "Bearer ${TwitchConstants.extractAccessToken(uri)}"

            TwitchSharedPreferences.setAccessToken(this, accessToken)
            launchMainActivity()
        }
    }

    private fun loginWithTwitch () {
        val twitchAccessToken = TwitchSharedPreferences.getAccessToken(this)

        if (twitchAccessToken != null) {
            launchMainActivity()
        } else {
            val twitchIntent = Intent(
                Intent.ACTION_VIEW, Uri.parse(TwitchConstants.TWITCH_AUTH_URL)
            )

            binding.btEntryToMain.setOnClickListener {
                startActivity(twitchIntent)
            }
        }
    }

    private fun launchMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
    }
}