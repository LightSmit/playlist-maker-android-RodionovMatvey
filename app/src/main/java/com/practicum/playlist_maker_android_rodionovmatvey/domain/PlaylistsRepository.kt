package com.practicum.playlist_maker_android_rodionovmatvey.domain

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Playlist
import kotlinx.coroutines.flow.Flow

interface PlaylistsRepository {
    fun getPlaylist(playlistId: Long): Flow<Playlist?>

    fun getAllPlaylists(): Flow<List<Playlist>>

    suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverImageUri: String?
    )

    suspend fun deletePlaylistById(id: Long)
}