package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Playlist
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track
import com.practicum.playlist_maker_android_rodionovmatvey.domain.PlaylistsRepository
import com.practicum.playlist_maker_android_rodionovmatvey.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PlaylistsViewModel : ViewModel() {
    private val playlistsRepository: PlaylistsRepository = Creator.getPlaylistsRepository()
    private val tracksRepository: TracksRepository = Creator.getTracksRepository()

    val playlists: Flow<List<Playlist>> = playlistsRepository.getAllPlaylists()
    val favoriteList: Flow<List<Track>> = tracksRepository.getFavoriteTracks()

    fun createNewPlayList(
        namePlaylist: String,
        description: String,
        coverImageUri: String? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.addNewPlaylist(
                name = namePlaylist,
                description = description,
                coverImageUri = coverImageUri
            )
        }
    }

    fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.insertTrackToPlaylist(track, playlistId)
        }
    }

    fun toggleFavorite(track: Track, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.updateTrackFavoriteStatus(track, isFavorite)
        }
    }

    fun deleteTrackFromPlaylist(track: Track) {
        viewModelScope.launch(Dispatchers.IO) {
            tracksRepository.deleteTrackFromPlaylist(track)
        }
    }

    fun deletePlaylistById(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            playlistsRepository.deletePlaylistById(id)
        }
    }
}