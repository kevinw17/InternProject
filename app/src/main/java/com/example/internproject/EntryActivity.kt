package com.example.internproject

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.internproject.databinding.ActivityEntryBinding

class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shaaredPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        val twitchAccessToken = shaaredPreferences.getString("twitch_access_token", null)

        if (twitchAccessToken != null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
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
    }
    override fun onResume() {
        super.onResume()
        val uri:Uri? = intent.data
        if(uri != null) {
            val accessToken = uri.fragment?.split("&")?.map { it.split("=") }?.find { it[0] == "access_token" }?.get(1)

            if (accessToken != null) {
                val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE).edit()
                sharedPreferences.putString("twitch_access_token", accessToken)
                sharedPreferences.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "No Access Token", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    override fun onStop() {
//        super.onStop()
//        val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE).edit()
//        sharedPreferences.remove("twitch_access_token")
//        sharedPreferences.apply()
//    }

}