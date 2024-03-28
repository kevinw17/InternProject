package com.example.internproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.internproject.databinding.FragmentFollowedStreamsBinding
import kotlinx.coroutines.launch

class FollowedStreamFragment : Fragment() {

    private lateinit var binding: FragmentFollowedStreamsBinding
    private lateinit var twitchViewModel: TwitchViewModel
    private var accessToken: String? = null
    private  var followedStreamsAdapter = FollowedStreamsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowedStreamsBinding.inflate(inflater, container, false)
        twitchViewModel = ViewModelProvider(this)[TwitchViewModel::class.java]
        getAccessTokenArguments()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeStreamResult()
        getStreamResult()
    }

    private fun setupRecyclerView() {
        binding.streamsRecyclerView.apply {
            adapter = followedStreamsAdapter
            binding.streamsRecyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    private fun getAccessTokenArguments() {
        accessToken = arguments?.getString(TwitchConstants.TOKEN_ARGUMENT)
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
                        if (streams != null) {
                            followedStreamsAdapter.submitList(streams)
                        }
                    }
                }
            }
        }
    }
}