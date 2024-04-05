package com.example.internproject.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberAsyncImagePainter
import com.example.internproject.model.Stream
import com.example.internproject.util.TwitchConstants
import com.example.internproject.viewmodel.TwitchViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ComposeFragment : Fragment() {

    private val twitchViewModel : TwitchViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                StreamsList()
            }
        }
    }

    @Composable
    private fun StreamsList() {
        getAccessToken()

        val streams by twitchViewModel.result.collectAsState()

        streams?.let {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(4.dp),
                modifier = Modifier.padding(4.dp)
            ) {
                items(it.size) {index ->
                    val stream = it[index]
                    StreamItemCard(stream = stream)
                }
            }

        }

    }

    @Composable
    private fun StreamItemCard(stream: Stream) {

        val expanded = remember {
            mutableStateOf(false)
        }

        val isVisible = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = true) {
            isVisible.value = true
        }

        MaterialTheme {
            AnimatedVisibility(
                visible = isVisible.value,
                enter = fadeIn(animationSpec = tween(1000))
            ) {
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    elevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        val imageUrl = stream.thumbnailUrl.replace("{width}", TwitchConstants.IMAGE_WIDTH.toString())
                            .replace("{height}", TwitchConstants.IMAGE_HEIGHT.toString())
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "Thumbnail",
                            modifier = Modifier
                                .height(150.dp)
                                .fillMaxWidth()
                        )
                        Text(text = stream.userName, modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.h5)

                        AnimatedVisibility(
                            visible = expanded.value,
                            enter = fadeIn(animationSpec = tween(1000)),
                            exit = fadeOut(animationSpec = tween(1000))

                        ) {
                            Column {
                                Text(text = stream.title, modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.body1)
                                Text(text = "Viewers: ${stream.viewerCount}", modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.body1)
                            }
                        }

                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
                            IconButton(onClick = { expanded.value = !expanded.value }) {
                                Icon(
                                    imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    contentDescription = "Expand/Collapse"
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getAccessToken() {
        val accessToken = arguments?.getString(TwitchConstants.TOKEN_ARGUMENT)

        if (accessToken != null) {
            twitchViewModel.getStreams(accessToken, TwitchConstants.CLIENT_ID)
        }
    }

}

