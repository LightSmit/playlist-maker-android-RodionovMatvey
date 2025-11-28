package com.practicum.playlist_maker_android_rodionovmatvey.domain

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track

interface TracksRepository {
    suspend fun searchTracks(expression: String): List<Track>
}