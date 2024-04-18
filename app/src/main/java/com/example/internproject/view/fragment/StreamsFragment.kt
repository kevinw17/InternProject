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
import com.example.internproject.adapter.StreamsAdapter
import com.example.internproject.databinding.FragmentStreamsBinding
import com.example.internproject.util.TwitchConstants
import com.example.internproject.util.TwitchSharedPreferences
import com.example.internproject.viewmodel.TwitchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StreamsFragment : Fragment() {

    private lateinit var binding: FragmentStreamsBinding
    private val twitchViewModel: TwitchViewModel by activityViewModels<TwitchViewModel>()
    private var accessToken: String? = null
    private val streamsAdapter = StreamsAdapter()

    companion object {

        const val TAG = "com.example.internproject.view.fragment.StreamsFragment"
        fun newInstance(): StreamsFragment {
            return StreamsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStreamsBinding.inflate(inflater, container, false)
        getAccessTokenArguments()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        getStreamResult()
        observeStreamResult()
    }

    private fun getAccessTokenArguments() {
        accessToken = context?.let { TwitchSharedPreferences.getAccessToken(it) }
    }

    private fun getStreamResult() {
        accessToken?.let { safeToken ->
            twitchViewModel.getStreams(
                authorizationToken = safeToken,
                clientId = TwitchConstants.CLIENT_ID,
            )
        }
    }

    private fun setupRecyclerView() {
        binding.rvStreams.apply {
            adapter = streamsAdapter
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun observeStreamResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    binding.pbStreams.visibility = View.VISIBLE
                    twitchViewModel.streams.collect { streams ->
                        binding.pbStreams.visibility = View.GONE
                        if (streams != null) {
                            streamsAdapter.submitList(streams)
                        }
                    }
                }
            }
        }
    }
}