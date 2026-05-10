package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import com.practicum.playlist_maker_android_rodionovmatvey.data.db.TrackDao
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.toEntity
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.toTrack
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TrackDto
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchRequest
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchResponse
import com.practicum.playlist_maker_android_rodionovmatvey.domain.NetworkClient
import com.practicum.playlist_maker_android_rodionovmatvey.domain.Resource
import com.practicum.playlist_maker_android_rodionovmatvey.domain.TracksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.math.abs

class TracksRepositoryImpl(
    private val networkClient: NetworkClient,
    private val trackDao: TrackDao
) : TracksRepository {

    override suspend fun searchTracks(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(
            TracksSearchRequest(expression = expression.trim())
        )

        return when (response.resultCode) {
            200 -> {
                val tracksResponse = response as TracksSearchResponse

                val tracks = tracksResponse.results.orEmpty().map { dto ->
                    val newTrack = dto.toTrack()
                    val savedTrack = trackDao.getTrackByIdOnce(newTrack.id)?.toTrack()

                    val actualTrack = newTrack.copy(
                        favorite = savedTrack?.favorite ?: false,
                        playlistId = savedTrack?.playlistId ?: 0
                    )

                    trackDao.insertTrack(actualTrack.toEntity())

                    actualTrack
                }

                Resource.Success(tracks)
            }

            else -> {
                Resource.Error(
                    response.errorMessage ?: "Ошибка сервера"
                )
            }
        }
    }

    override fun getTrackById(trackId: Long): Flow<Track?> {
        return trackDao.getTrackById(trackId).map { entity ->
            entity?.toTrack()
        }
    }

    override fun getTrackByNameAndArtist(track: Track): Flow<Track?> {
        return trackDao.getTrackByNameAndArtist(
            name = track.trackName,
            artist = track.artistName
        ).map { entity ->
            entity?.toTrack()
        }
    }

    override fun getFavoriteTracks(): Flow<List<Track>> {
        return trackDao.getFavoriteTracks().map { entities ->
            entities.map { it.toTrack() }
        }
    }

    override suspend fun insertTrackToPlaylist(track: Track, playlistId: Long) {
        val savedTrack = trackDao.getTrackByIdOnce(track.id)?.toTrack()

        val actualTrack = track.copy(
            playlistId = playlistId,
            favorite = savedTrack?.favorite ?: track.favorite
        )

        trackDao.insertTrack(actualTrack.toEntity())
    }

    override suspend fun deleteTrackFromPlaylist(track: Track) {
        val savedTrack = trackDao.getTrackByIdOnce(track.id)?.toTrack() ?: track
        val updatedTrack = savedTrack.copy(playlistId = 0)

        if (updatedTrack.favorite) {
            trackDao.insertTrack(updatedTrack.toEntity())
        } else {
            trackDao.deleteTrackById(updatedTrack.id)
        }
    }

    override suspend fun updateTrackFavoriteStatus(track: Track, isFavorite: Boolean) {
        val savedTrack = trackDao.getTrackByIdOnce(track.id)?.toTrack() ?: track

        val updatedTrack = savedTrack.copy(favorite = isFavorite)

        if (updatedTrack.favorite || updatedTrack.playlistId != 0L) {
            trackDao.insertTrack(updatedTrack.toEntity())
        } else {
            trackDao.deleteTrackById(updatedTrack.id)
        }
    }

    override fun deleteTracksByPlaylistId(playlistId: Long) {
        // Для совместимости с текущим ViewModel метод оставлен не suspend.
        // Реальную очистку плейлистов делаем в PlaylistsRepositoryImpl.
    }

    private fun TrackDto.toTrack(): Track {
        val safeTrackName = trackName ?: "Неизвестный трек"
        val safeArtistName = artistName ?: "Неизвестный исполнитель"

        return Track(
            id = id ?: generateFallbackId(safeTrackName, safeArtistName),
            trackName = safeTrackName,
            artistName = safeArtistName,
            trackTime = formatTrackTime(trackTimeMillis ?: 0L),
            artworkUrl100 = artworkUrl100,
            previewUrl = previewUrl
        )
    }

    private fun formatTrackTime(trackTimeMillis: Long): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(trackTimeMillis)
    }

    private fun generateFallbackId(trackName: String, artistName: String): Long {
        return abs("$trackName$artistName".hashCode()).toLong()
    }
}