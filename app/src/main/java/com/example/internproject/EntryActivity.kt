package com.example.internproject

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.lifecycleScope
import com.example.internproject.databinding.ActivityEntryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import twitter4j.Twitter
import twitter4j.TwitterFactory
import twitter4j.auth.AccessToken
import twitter4j.conf.ConfigurationBuilder

class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding
    lateinit var twitterDialog: Dialog
    lateinit var twitter: Twitter
    var accToken: AccessToken? = null

    private var id = ""
    private var handle = ""
    private var name = ""
    private var email = ""
    private var profilePicURL = ""
    private var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clientId = "buojwn4j7smykbken5koabb6tg9m9m"
        val redirectUri = "https://com.example.internproject"


        val twitchIntent = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "https://id.twitch.tv/oauth2/authorize?client_id=$clientId&redirect_uri=$redirectUri&response_type=token&scope=user:read:follows")
        )

        binding.btnToMain.setOnClickListener {
            startActivity(twitchIntent)
        }

    }

    override fun onResume() {
        super.onResume()
        val uri:Uri? = intent.data
        if(uri != null) {
            Log.i("Twitch", uri.toString())
        }
    }

}
