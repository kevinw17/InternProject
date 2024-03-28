package com.example.internproject

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
import com.example.internproject.databinding.FragmentStreamsBinding
import kotlinx.coroutines.delay
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
            layoutManager = GridLayoutManager(context, 2)
        }
    }

    private fun observeStreamResult() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    twitchViewModel.result.collect { streams ->
                        if (streams.isNullOrEmpty()) {
                            delay(1000)
                            binding.textNoStreams.visibility = View.VISIBLE
                            binding.streamsRecyclerView.visibility = View.GONE
                        } else {
                            binding.textNoStreams.visibility = View.GONE
                            binding.streamsRecyclerView.visibility = View.VISIBLE
                            streamsAdapter.submitList(streams)
                        }
                    }
                }
            }
        }
    }
}