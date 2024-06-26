package com.example.internproject.view.fragment

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.internproject.viewmodel.TwitchViewModel

@Composable
fun StreamsList(twitchViewModel : TwitchViewModel) {

    val streams by twitchViewModel.streams.collectAsState(null)
    val isLoading by twitchViewModel.isLoading.collectAsState()

    if(isLoading){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator(color = Color.Black)
        }
    }
    else {
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

}