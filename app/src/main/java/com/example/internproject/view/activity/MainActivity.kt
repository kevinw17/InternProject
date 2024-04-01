package com.example.internproject.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.internproject.R
import com.example.internproject.databinding.ActivityMainBinding
import com.example.internproject.util.TwitchConstants
import com.example.internproject.view.fragment.FollowedStreamsFragment
import com.example.internproject.view.fragment.StreamsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            streamsNav()
            binding.bottomNavigationView.selectedItemId = R.id.navigation_streams
        }

        bottomNav()

    }
    private fun bottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navigation_streams -> {
                    streamsNav()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_followed_streams -> {
                    followedStreamNav()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    Toast.makeText(this, "Notifications", Toast.LENGTH_SHORT).show()
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }
    private fun streamsNav() {
        val accessToken = intent.getStringExtra(TwitchConstants.TOKEN_ARGUMENT)
        val fragmentStreams = StreamsFragment()
        val args = bundleOf(TwitchConstants.TOKEN_ARGUMENT to accessToken)

        fragmentStreams.arguments = args

        loadFragment(fragmentStreams)
    }
    private fun followedStreamNav() {
        val accessToken = intent.getStringExtra(TwitchConstants.TOKEN_ARGUMENT)
        val fragmentFollowedStream = FollowedStreamsFragment()
        val args = bundleOf(TwitchConstants.TOKEN_ARGUMENT to accessToken)

        fragmentFollowedStream.arguments = args

        loadFragment(fragmentFollowedStream)
    }

    private fun loadFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager

        val fragmentTransaction : FragmentTransaction =
            fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }

}