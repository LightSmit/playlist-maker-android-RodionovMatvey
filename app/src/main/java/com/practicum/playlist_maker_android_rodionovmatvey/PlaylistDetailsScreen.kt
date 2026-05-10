package com.practicum.playlist_maker_android_rodionovmatvey


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track
import com.practicum.playlist_maker_android_rodionovmatvey.ui.search.TrackListItem
import java.io.File

@Composable
fun PlaylistDetailsScreen(
    playlistId: Long,
    onBackClick: () -> Unit,
    navigateToTrack: (Long) -> Unit = {},
    playlistsViewModel: PlaylistsViewModel = viewModel()
) {
    val playlists by playlistsViewModel.playlists.collectAsStateWithLifecycle(initialValue = emptyList())
    val playlist = playlists.find { it.id == playlistId }

    var trackToDelete by remember { mutableStateOf<Track?>(null) }

    if (playlist == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.action_back)
                    )
                }

                Text(
                    text = stringResource(R.string.playlist_not_found),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        return
    }

    val tracksCount = playlist.tracks.size

    val tracksCountText = pluralStringResource(
        id = R.plurals.tracks_count,
        count = tracksCount,
        tracksCount
    )

    val totalMinutes = remember(playlist.tracks) {
        playlist.tracks.sumOf {
            parseTrackTimeToSeconds(it.trackTime)
        } / 60
    }

    val minutesText = pluralStringResource(
        id = R.plurals.minutes_count,
        count = totalMinutes,
        totalMinutes
    )

    val durationText = if (playlist.tracks.isEmpty()) {
        tracksCountText
    } else {
        stringResource(
            R.string.playlist_duration_tracks_format,
            minutesText,
            tracksCountText
        )
    }

    val coverUrl = playlist.coverImageUri

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
    ) {
        item {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.action_back)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = coverUrl?.let { File(it) },
                contentDescription = playlist.name,
                placeholder = painterResource(id = R.drawable.ic_music),
                error = painterResource(id = R.drawable.ic_music),
                fallback = painterResource(id = R.drawable.ic_music),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(androidx.compose.ui.graphics.Color.LightGray)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = playlist.name,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )

            if (playlist.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = playlist.description,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = durationText,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Icon(
                imageVector = Icons.Filled.MoreVert,
                contentDescription = stringResource(R.string.playlist_menu),
                modifier = Modifier.size(32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (playlist.tracks.isEmpty()) {
                Text(
                    text = stringResource(R.string.playlist_empty_tracks),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 80.dp)
                )
            }
        }

        items(
            items = playlist.tracks,
            key = { track -> track.id },
            contentType = { "track" }
        ) { track ->
            TrackListItem(
                track = track,
                onClick = { navigateToTrack(track.id) },
                onLongClick = {
                    trackToDelete = track
                }
            )
            HorizontalDivider(thickness = 0.5.dp)
        }
    }

    if (trackToDelete != null) {
        AlertDialog(
            onDismissRequest = {
                trackToDelete = null
            },
            title = {
                Text(text = stringResource(R.string.delete_track_from_playlist_title))
            },
            text = {
                Text(text = stringResource(R.string.delete_track_from_playlist_message))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        trackToDelete?.let { track ->
                            playlistsViewModel.deleteTrackFromPlaylist(track)
                        }
                        trackToDelete = null
                    }
                ) {
                    Text(text = stringResource(R.string.action_delete))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        trackToDelete = null
                    }
                ) {
                    Text(text = stringResource(R.string.action_cancel))
                }
            }
        )
    }
}

private fun parseTrackTimeToSeconds(time: String): Int {
    val parts = time.split(":")
    if (parts.size != 2) return 0

    val minutes = parts[0].toIntOrNull() ?: 0
    val seconds = parts[1].toIntOrNull() ?: 0

    return minutes * 60 + seconds
}
