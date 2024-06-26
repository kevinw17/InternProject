package com.example.internproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internproject.adapter.VideosAdapter
import com.example.internproject.databinding.FragmentVideosBinding
import com.example.internproject.util.TwitchConstants
import com.example.internproject.util.TwitchSharedPreferences
import com.example.internproject.viewmodel.TwitchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VideosFragment : Fragment() {

    private lateinit var binding : FragmentVideosBinding
    private val twitchViewModel : TwitchViewModel by activityViewModels<TwitchViewModel>()
    private var accessToken: String? = null
    private var videosAdapter = VideosAdapter()

    companion object {

        const val TAG = "com.example.internproject.view.fragment.VideosFragment"
        fun newInstance(): VideosFragment {
            return VideosFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentVideosBinding.inflate(inflater, container, false)
        getAccessTokenArguments()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getVideoResult()
        observeVideoResult()
    }

    private fun getAccessTokenArguments() {
        accessToken = context?.let { TwitchSharedPreferences.getAccessToken(it) }
    }

    private fun setupRecyclerView() {
        binding.rvVideos.apply {
            adapter = videosAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun getVideoResult() {
        accessToken?.let { safeToken ->
            twitchViewModel.getVideos(
                authorizationToken = safeToken,
                clientId = TwitchConstants.CLIENT_ID,
                userId = TwitchConstants.VIDEO_USER_ID,
                type = TwitchConstants.VIDEO_TYPE
            )
        }
    }

    private fun observeVideoResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    twitchViewModel.isLoading.collect { isLoading ->
                        binding.pbVideos.visibility = if (isLoading) View.VISIBLE else View.GONE
                    }
                }
                launch {
                    twitchViewModel.videos.collect { videos ->
                        if (videos != null) {
                            videosAdapter.submitList(videos)
                        }
                    }
                }
            }
        }
    }

}