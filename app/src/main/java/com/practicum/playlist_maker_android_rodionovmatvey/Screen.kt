package com.practicum.playlist_maker_android_rodionovmatvey

enum class Screen(val route: String) {
    MAIN("main"),
    SEARCH("search"),
    SETTINGS("settings"),
    PLAYLISTS("playlists"),
    FAVORITES("favorites"),
    CREATE_PLAYLIST("create_playlist"),
    TRACK_DETAILS("track_details/{trackId}"),
    PLAYLIST_DETAILS("playlist_details/{playlistId}");

    companion object {
        fun trackDetailsRoute(trackId: Long): String = "track_details/$trackId"
        fun playlistDetailsRoute(playlistId: Long): String = "playlist_details/$playlistId"
    }
}