package com.practicum.playlist_maker_android_rodionovmatvey.data.dto

import com.google.gson.annotations.SerializedName

data class TrackDto(
    @SerializedName("trackId")
    val id: Long? = null,
    val trackName: String? = null,
    val artistName: String? = null,
    val trackTimeMillis: Long? = null,
    val previewUrl: String? = null,
    val artworkUrl100: String? = null
)