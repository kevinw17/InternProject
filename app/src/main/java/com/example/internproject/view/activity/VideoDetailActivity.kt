package com.example.internproject.view.activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.example.internproject.databinding.ActivityVideoDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoDetailBinding
    private val videoUrl by lazy { intent.getStringExtra("VIDEO_URL") }
    private val videoTitle by lazy { intent.getStringExtra("VIDEO_TITLE") }
    private val viewCount by lazy { intent.getIntExtra("VIDEO_VIEW_COUNT", 0).toString() }
    private val streamer by lazy { intent.getStringExtra("VIDEO_STREAMER") }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureWebView()
        loadTwitchVideo()
    }

    private fun configureWebView () {
        binding.wvTwitchVideo.settings.javaScriptEnabled = true
        binding.wvTwitchVideo.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                if (url != null) {
                    view?.loadUrl(url)
                }
                return true
            }
        }
    }

    private fun loadTwitchVideo () {
        if (videoUrl != null) {
            binding.wvTwitchVideo.loadUrl(videoUrl!!)
        }

        binding.tvUserName.text = streamer
        binding.tvVideoTitle.text = videoTitle
        binding.tvViewCount.text = "Total Views : $viewCount"

    }

}