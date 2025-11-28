package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import kotlinx.coroutines.delay
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchRequest
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchResponse
import com.practicum.playlist_maker_android_rodionovmatvey.domain.NetworkClient
import com.practicum.playlist_maker_android_rodionovmatvey.domain.TracksRepository

class TracksRepositoryImpl(
    private val networkClient: NetworkClient
) : TracksRepository {

    override suspend fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        delay(1000)

        return if (response.resultCode == 200) {
            val tracksResponse = response as TracksSearchResponse
            tracksResponse.results.map { dto ->
                val seconds = dto.trackTimeMillis / 1000
                val minutes = seconds / 60
                val trackTime = "%02d:%02d".format(
                    minutes,
                    seconds - minutes * 60
                )
                Track(
                    trackName = dto.trackName,
                    artistName = dto.artistName,
                    trackTime = trackTime
                )
            }
        } else {
            emptyList()
        }
    }
}