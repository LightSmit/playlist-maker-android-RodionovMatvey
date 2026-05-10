package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import kotlinx.coroutines.flow.distinctUntilChanged
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.PlaylistDao
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.PlaylistEntity
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.TrackDao
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.toPlaylist
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.toTrack
import com.practicum.playlist_maker_android_rodionovmatvey.domain.PlaylistsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class PlaylistsRepositoryImpl(
    private val playlistDao: PlaylistDao,
    private val trackDao: TrackDao
) : PlaylistsRepository {

    override fun getPlaylist(playlistId: Long): Flow<Playlist?> {
        return combine(
            playlistDao.getPlaylistById(playlistId).distinctUntilChanged(),
            trackDao.getTracksByPlaylistId(playlistId).distinctUntilChanged()
        ) { playlistEntity, trackEntities ->
            playlistEntity?.toPlaylist(
                tracks = trackEntities.map { it.toTrack() }
            )
        }.distinctUntilChanged()
    }

    override fun getAllPlaylists(): Flow<List<Playlist>> {
        return combine(
            playlistDao.getAllPlaylists().distinctUntilChanged(),
            trackDao.getAllTracks().distinctUntilChanged()
        ) { playlistEntities, trackEntities ->
            playlistEntities.map { playlistEntity ->
                playlistEntity.toPlaylist(
                    tracks = trackEntities
                        .filter { it.playlistId == playlistEntity.id }
                        .map { it.toTrack() }
                )
            }
        }.distinctUntilChanged()
    }

    override suspend fun addNewPlaylist(
        name: String,
        description: String,
        coverImageUri: String?
    ) {
        playlistDao.insertPlaylist(
            PlaylistEntity(
                name = name,
                description = description,
                coverImageUri = coverImageUri
            )
        )
    }

    override suspend fun deletePlaylistById(id: Long) {
        trackDao.deleteNonFavoriteTracksByPlaylistId(id)
        trackDao.deleteTracksByPlaylistId(id)
        playlistDao.deletePlaylistById(id)
    }
}