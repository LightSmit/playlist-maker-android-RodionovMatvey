package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Playlist
import java.io.File

@Composable
fun PlaylistListItem(
    playlist: Playlist,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val tracksCountText = context.resources.getQuantityString(
        R.plurals.tracks_count,
        playlist.tracks.size,
        playlist.tracks.size
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE6E6E6)),
            contentAlignment = Alignment.Center
        ) {
            if (playlist.coverImageUri != null) {
                AsyncImage(
                    model = File(playlist.coverImageUri),
                    contentDescription = playlist.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    imageVector = Icons.Filled.LibraryMusic,
                    contentDescription = playlist.name,
                    colorFilter = ColorFilter.tint(Color.Gray),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }

        Column {
            Text(
                text = playlist.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                maxLines = 1
            )

            Text(
                text = tracksCountText,
                fontSize = 12.sp,
                color = Color.Gray,
                maxLines = 1
            )
        }
    }
}