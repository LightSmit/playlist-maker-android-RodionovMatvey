package com.practicum.playlist_maker_android_rodionovmatvey.data.network

data class Playlist(
    val id: Long = 0,
    val name: String,
    val description: String,
    val coverImageUri: String? = null,
    var tracks: List<Track>
)