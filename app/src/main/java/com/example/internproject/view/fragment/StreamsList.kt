package com.example.internproject.view.fragment

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.internproject.viewmodel.TwitchViewModel

@Composable
fun StreamsList(twitchViewModel : TwitchViewModel) {

    val streams by twitchViewModel.streams.collectAsState()

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