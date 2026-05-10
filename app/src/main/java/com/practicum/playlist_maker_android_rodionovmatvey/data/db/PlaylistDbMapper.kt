package com.practicum.playlist_maker_android_rodionovmatvey.data.db

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Playlist
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track

fun PlaylistEntity.toPlaylist(tracks: List<Track>): Playlist {
    return Playlist(
        id = id,
        name = name,
        description = description,
        coverImageUri = coverImageUri,
        tracks = tracks
    )
}