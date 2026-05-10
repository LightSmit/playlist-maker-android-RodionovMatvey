package com.practicum.playlist_maker_android_rodionovmatvey.domain

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track
import kotlinx.coroutines.flow.Flow

interface TracksRepository {
    suspend fun searchTracks(expression: String): Resource<List<Track>>

    fun getTrackById(trackId: Long): Flow<Track?>

    fun getTrackByNameAndArtist(track: Track): Flow<Track?>

    fun getFavoriteTracks(): Flow<List<Track>>

    suspend fun insertTrackToPlaylist(track: Track, playlistId: Long)

    suspend fun deleteTrackFromPlaylist(track: Track)

    suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean)

    fun deleteTracksByPlaylistId(playlistId: Long)
}