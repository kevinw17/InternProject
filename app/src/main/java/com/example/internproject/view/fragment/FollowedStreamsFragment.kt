package com.example.internproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.internproject.adapter.FollowedStreamsAdapter
import com.example.internproject.databinding.FragmentFollowedStreamsBinding
import com.example.internproject.util.TwitchConstants
import com.example.internproject.util.TwitchSharedPreferences
import com.example.internproject.viewmodel.TwitchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FollowedStreamsFragment : Fragment() {

    private lateinit var binding: FragmentFollowedStreamsBinding
    private lateinit var twitchViewModel: TwitchViewModel
    private var accessToken: String? = null
    private  var followedStreamsAdapter = FollowedStreamsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowedStreamsBinding.inflate(inflater, container, false)
        twitchViewModel = ViewModelProvider(this)[TwitchViewModel::class.java]
        getAccessTokenArguments()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getStreamResult()
        observeStreamResult()
    }

    private fun setupRecyclerView() {
        binding.streamsRecyclerView.apply {
            adapter = followedStreamsAdapter
            binding.streamsRecyclerView.layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun getAccessTokenArguments() {
        accessToken = context?.let { TwitchSharedPreferences.getAccessToken(it) }
    }

    private fun getStreamResult() {
        accessToken?.let { safeToken ->
            twitchViewModel.getFollowedStreams(
                authorizationToken = safeToken,
                clientId = TwitchConstants.CLIENT_ID,
                userId = TwitchConstants.DUMMY_USER_ID
            )
        }
    }

    private fun observeStreamResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    twitchViewModel.result.collect { streams ->
                        if(streams.isNullOrEmpty()) {
                            binding.textNoStreams.visibility = View.VISIBLE
                            binding.streamsRecyclerView.visibility = View.GONE
                        }
                        else {
                            binding.textNoStreams.visibility = View.GONE
                            binding.streamsRecyclerView.visibility = View.VISIBLE
                            followedStreamsAdapter.submitList(streams)
                        }
                    }
                }
            }
        }
    }
}