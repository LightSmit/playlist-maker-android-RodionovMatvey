package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import com.practicum.playlist_maker_android_rodionovmatvey.data.preferences.SearchHistoryPreferences
import com.practicum.playlist_maker_android_rodionovmatvey.domain.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val preferences: SearchHistoryPreferences
) : SearchHistoryRepository {

    override fun addEntry(word: String) {
        preferences.addEntry(word)
    }

    override suspend fun getEntries(): List<String> {
        return preferences.getEntries()
    }
}