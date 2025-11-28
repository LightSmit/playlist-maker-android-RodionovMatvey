package com.practicum.playlist_maker_android_rodionovmatvey.ui.search

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track

sealed class SearchState {
    object Initial : SearchState()
    object Searching : SearchState()
    data class Success(val tracks: List<Track>) : SearchState()
    data class Fail(val error: String) : SearchState()
}