package com.practicum.playlist_maker_android_rodionovmatvey.data.network

data class Track(
    val id: Long,
    val trackName: String,
    val artistName: String,
    val trackTime: String,
    val artworkUrl100: String? = null,
    val previewUrl: String? = null,
    val favorite: Boolean = false,
    val playlistId: Long = 0
)