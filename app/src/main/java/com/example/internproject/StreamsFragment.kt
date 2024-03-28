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
import com.example.internproject.databinding.FragmentStreamsBinding
import kotlinx.coroutines.launch

class StreamsFragment : Fragment() {

    private lateinit var binding: FragmentStreamsBinding
    private lateinit var twitchViewModel: TwitchViewModel
    private var accessToken: String? = null
    private val streamsAdapter = StreamsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStreamsBinding.inflate(inflater, container, false)
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

    private fun getAccessTokenArguments() {
        accessToken = arguments?.getString(TwitchConstants.TOKEN_ARGUMENT)
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
        binding.streamsRecyclerView.apply {
            adapter = streamsAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun observeStreamResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    twitchViewModel.result.collect { streams ->
                        if (streams != null) {
                            streamsAdapter.submitList(streams)
                        }
                    }
                }
            }
        }
    }
}