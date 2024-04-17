package com.example.internproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.internproject.util.TwitchConstants
import com.example.internproject.util.TwitchSharedPreferences
import com.example.internproject.viewmodel.TwitchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeFragment : Fragment() {

    private val twitchViewModel: TwitchViewModel by activityViewModels<TwitchViewModel>()

    companion object {

        const val TAG = "com.example.internproject.view.fragment.ComposeFragment"
        fun newInstance(): ComposeFragment {
            return ComposeFragment()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                getAccessToken()
                StreamsList(twitchViewModel)
            }
        }
    }

    private fun getAccessToken() {
        val accessToken = context?.let { TwitchSharedPreferences.getAccessToken(it) }

        if (accessToken != null) {
            twitchViewModel.getStreams(accessToken, TwitchConstants.CLIENT_ID)
        }
    }

}

