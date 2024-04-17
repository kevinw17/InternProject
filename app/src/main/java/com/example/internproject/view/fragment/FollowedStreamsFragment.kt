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
    private val twitchViewModel: TwitchViewModel by activityViewModels<TwitchViewModel>()
    private var accessToken: String? = null
    private  var followedStreamsAdapter = FollowedStreamsAdapter()

    companion object {

        const val TAG = "com.example.internproject.view.fragment.FollowedStreamsFragment"
        fun newInstance(): FollowedStreamsFragment {
            return FollowedStreamsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowedStreamsBinding.inflate(inflater, container, false)
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
        binding.rvFollowedStreams.apply {
            adapter = followedStreamsAdapter
            binding.rvFollowedStreams.layoutManager = GridLayoutManager(context, 2)
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
                    twitchViewModel.followedStreams.collect { streams ->
                        if(streams.isNullOrEmpty()) {
                            binding.tvNoStreamAvailable.visibility = View.VISIBLE
                            binding.rvFollowedStreams.visibility = View.GONE
                        }
                        else {
                            binding.tvNoStreamAvailable.visibility = View.GONE
                            binding.rvFollowedStreams.visibility = View.VISIBLE
                            followedStreamsAdapter.submitList(streams)
                        }
                    }
                }
            }
        }
    }
}