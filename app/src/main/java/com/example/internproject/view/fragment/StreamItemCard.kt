package com.example.internproject.view.fragment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.internproject.model.Stream
import com.example.internproject.util.TwitchConstants

@Composable
fun StreamItemCard(stream: Stream) {

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
                elevation = 2.dp,
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    ThumbnailImage(stream = stream)
                    StreamUserName(stream = stream, modifier = Modifier)
                    ExpandStreamInfo(stream = stream, expanded = expanded)
                    ExpandCollapseButton(expanded = expanded)
                }
            }
        }
    }
}

@Composable
fun ThumbnailImage(stream: Stream) {

    val imageUrl = stream.thumbnailUrl.replace("{width}", TwitchConstants.IMAGE_WIDTH.toString())
        .replace("{height}", TwitchConstants.IMAGE_HEIGHT.toString())

    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = "Thumbnail",
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth()
    )
}

@Composable
fun StreamUserName(stream: Stream, modifier: Modifier) {
    
    Text(text = stream.userName, modifier = modifier.padding(2.dp), style = MaterialTheme.typography.body1)
    
}

@Composable
fun ExpandStreamInfo(stream: Stream, expanded : MutableState<Boolean>) {

    AnimatedVisibility(
        visible = expanded.value,
        enter = fadeIn(animationSpec = tween(1000))
    ) {
        Column {
            StreamTitle(stream = stream)
            StreamViewerCount(stream = stream)
        }
    }
}

@Composable
fun StreamTitle(stream: Stream) {
    
    Text(text = stream.title, modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.body2)
    
}

@Composable
fun StreamViewerCount(stream: Stream) {
    
    Text(text = "Viewers: ${stream.viewerCount}", modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.body2)
    
}

@Composable
fun ExpandCollapseButton(expanded: MutableState<Boolean>) {
    
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){
        IconButton(onClick = { expanded.value = !expanded.value }) {
            Icon(
                imageVector = if (expanded.value) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                contentDescription = "Expand/Collapse"
            )
        }
    }
    
}

@Preview(showBackground = true)
@Composable
fun PreviewStreamItemCard() {
    StreamItemCard(stream = Stream(
        id = "123456789",
        userId = "98765",
        userLogin = "sandysanderman",
        userName = "sandysanderman",
        gameId = "494131",
        gameName = "Little Nightmares",
        type = "live",
        title = "hablamos y le damos a Little Nightmares 1",
        tags =  listOf("Espanol"),
        viewerCount = 78365,
        startedAt = "2021-03-10T15:04:21Z",
        language = "es",
        thumbnailUrl = "https://static-cdn.jtvnw.net/previews-ttv/live_user_auronplay-{width}x{height}.jpg",
        tagIds = listOf(""),
        isMature = false
    ))
}

