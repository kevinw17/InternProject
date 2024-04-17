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
    private lateinit var streamsFragment: StreamsFragment
    private lateinit var composeFragment: ComposeFragment
    private lateinit var followedStreamsFragment: FollowedStreamsFragment
    private lateinit var videosFragment: VideosFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        twitchViewModel = ViewModelProvider(this)[TwitchViewModel::class.java]
        setContentView(binding.root)
        binding.tbMainActivity.title = "Streams"
        gotoStreamFragment()
        bottomNav()
    }

    private fun bottomNav() {
        binding.bnMainActivity.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.navigation_streams -> {
                    binding.tbMainActivity.title = "Streams"
                    gotoStreamFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_compose -> {
                    binding.tbMainActivity.title = "Compose"
                    gotoComposeFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_followed_streams -> {
                    binding.tbMainActivity.title = "Followed Streams"
                    gotoFollowedFragment()
                    return@setOnItemSelectedListener true
                }
                R.id.navigation_videos -> {
                    binding.tbMainActivity.title = "Videos"
                    gotoVideoFragment()
                    return@setOnItemSelectedListener true
                }
                else -> false
            }
        }
    }

    private fun fragmentManagerContainsCurrentFragment(fragment: Fragment): Boolean =
        supportFragmentManager.fragments.contains(fragment)

    private fun gotoStreamFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (::streamsFragment.isInitialized.not() || fragmentManagerContainsCurrentFragment(streamsFragment).not()) {
            streamsFragment = StreamsFragment.newInstance()
            fragmentTransaction.add(R.id.fl_fragment_container, streamsFragment)
        }
        setActiveFragment(streamsFragment, fragmentTransaction)
    }

    private fun gotoComposeFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (::composeFragment.isInitialized.not() || fragmentManagerContainsCurrentFragment(composeFragment).not()) {
            composeFragment = ComposeFragment.newInstance()
            fragmentTransaction.add(R.id.fl_fragment_container, composeFragment)
        }
        setActiveFragment(composeFragment, fragmentTransaction)
    }

    private fun gotoFollowedFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (::followedStreamsFragment.isInitialized.not() || fragmentManagerContainsCurrentFragment(followedStreamsFragment).not()) {
            followedStreamsFragment = FollowedStreamsFragment.newInstance()
            fragmentTransaction.add(R.id.fl_fragment_container, followedStreamsFragment)
        }
        setActiveFragment(followedStreamsFragment, fragmentTransaction)
    }

    private fun gotoVideoFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (::videosFragment.isInitialized.not() || fragmentManagerContainsCurrentFragment(videosFragment).not()) {
            videosFragment = VideosFragment.newInstance()
            fragmentTransaction.add(R.id.fl_fragment_container, videosFragment)
        }
        setActiveFragment(videosFragment, fragmentTransaction)
    }

    private fun setActiveFragment(
        currentActiveFragment: Fragment,
        fragmentTransaction: FragmentTransaction
    ) {
        supportFragmentManager.fragments.forEach { fragment ->
            if (fragment == currentActiveFragment) {
                fragmentTransaction.show(currentActiveFragment)
            } else {
                fragmentTransaction.hide(fragment)
            }
        }
        fragmentTransaction.commit()
    }
}