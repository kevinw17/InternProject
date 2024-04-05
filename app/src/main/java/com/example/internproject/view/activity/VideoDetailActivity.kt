package com.example.internproject.view.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.internproject.databinding.ActivityVideoDetailBinding
import com.google.android.exoplayer2.ExoPlayer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding
    private var player : ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureWebView()
        loadTwitchVideo()
    }

    private fun configureWebView () {
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
    }

    private fun loadTwitchVideo () {
        val videoUrl = intent.getStringExtra("VIDEO_URL")
        val videoTitle = intent.getStringExtra("VIDEO_TITLE")
        val viewCount = intent.getIntExtra("VIDEO_VIEW_COUNT", 0).toString()
        val streamer = intent.getStringExtra("VIDEO_STREAMER")
        if (videoUrl != null) {
            binding.webView.loadUrl(videoUrl)
        }

        binding.userName.text = streamer
        binding.videoTitle.text = videoTitle
        binding.viewCount.text = "Total Views : $viewCount"

    }

}