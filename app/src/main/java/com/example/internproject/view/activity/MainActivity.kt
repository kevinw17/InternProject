package com.example.internproject.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.example.internproject.R
import com.example.internproject.databinding.ActivityMainBinding
import com.example.internproject.view.fragment.ComposeFragment
import com.example.internproject.view.fragment.FollowedStreamsFragment
import com.example.internproject.view.fragment.StreamsFragment
import com.example.internproject.view.fragment.VideosFragment
import com.example.internproject.viewmodel.TwitchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var twitchViewModel: TwitchViewModel
    private val streamsFragment by lazy { StreamsFragment.newInstance() }
    private val composeFragment by lazy { ComposeFragment.newInstance() }
    private val followedStreamsFragment by lazy { FollowedStreamsFragment.newInstance() }
    private val videosFragment by lazy { VideosFragment.newInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        twitchViewModel = ViewModelProvider(this)[TwitchViewModel::class.java]
        setContentView(binding.root)
        switchFragment(streamsFragment)
        bottomNav()
    }

    private fun bottomNav() {
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navigation_streams -> {
                    switchFragment(streamsFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_compose -> {
                    switchFragment(composeFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_followed_streams -> {
                    switchFragment(followedStreamsFragment)
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_videos -> {
                    switchFragment(videosFragment)
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun switchFragment(fragment: Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}