package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackDetailsScreen(
    trackId: Long,
    onBackClick: () -> Unit,
    playlistsViewModel: PlaylistsViewModel = viewModel()
) {
    val track by Creator.getTracksRepository()
        .getTrackById(trackId)
        .collectAsStateWithLifecycle(initialValue = null)

    val playlists by playlistsViewModel.playlists.collectAsStateWithLifecycle(initialValue = emptyList())

    var showBottomSheet by remember { mutableStateOf(false) }

    val currentTrack = track ?: return
    val artworkUrl = currentTrack.artworkUrl100?.replace("100x100bb", "512x512bb")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .padding(start = 24.dp, end = 24.dp, top = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.action_back)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = artworkUrl,
            contentDescription = currentTrack.trackName,
            placeholder = painterResource(id = R.drawable.ic_music),
            error = painterResource(id = R.drawable.ic_music),
            fallback = painterResource(id = R.drawable.ic_music),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(280.dp)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = currentTrack.trackName,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 2
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = currentTrack.artistName,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { showBottomSheet = true },
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFFE6E6E6), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(R.string.action_add_to_playlist),
                    tint = Color.Gray
                )
            }

            IconButton(
                onClick = {
                    playlistsViewModel.toggleFavorite(
                        track = currentTrack,
                        isFavorite = !currentTrack.favorite
                    )
                },
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFFE6E6E6), CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = stringResource(R.string.action_add_to_favorites),
                    tint = if (currentTrack.favorite) Color.Red else Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.track_duration),
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = currentTrack.trackTime,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false }
        ) {
            Text(
                text = stringResource(R.string.action_add_to_playlist),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                items(playlists) { playlist ->
                    PlaylistListItem(playlist = playlist) {
                        playlistsViewModel.insertTrackToPlaylist(
                            track = currentTrack,
                            playlistId = playlist.id
                        )
                        showBottomSheet = false
                    }
                    HorizontalDivider(thickness = 0.5.dp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}