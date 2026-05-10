package com.practicum.playlist_maker_android_rodionovmatvey.ui.search

import com.practicum.playlist_maker_android_rodionovmatvey.data.network.Track

sealed class SearchState {
    data object Initial : SearchState()
    data object Searching : SearchState()
    data object Empty : SearchState()
    data class Success(val tracks: List<Track>) : SearchState()
    data class Fail(val error: String) : SearchState()
}