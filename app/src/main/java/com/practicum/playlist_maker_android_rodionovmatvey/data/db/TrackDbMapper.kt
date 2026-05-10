package com.practicum.playlist_maker_android_rodionovmatvey.data.db

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track

fun Track.toEntity(): TrackEntity {
    return TrackEntity(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl100 = artworkUrl100,
        previewUrl = previewUrl,
        favorite = favorite,
        playlistId = playlistId
    )
}

fun TrackEntity.toTrack(): Track {
    return Track(
        id = id,
        trackName = trackName,
        artistName = artistName,
        trackTime = trackTime,
        artworkUrl100 = artworkUrl100,
        previewUrl = previewUrl,
        favorite = favorite,
        playlistId = playlistId
    )
}