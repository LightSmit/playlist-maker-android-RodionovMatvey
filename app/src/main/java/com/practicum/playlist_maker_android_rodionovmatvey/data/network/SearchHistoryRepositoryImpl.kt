package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import com.practicum.playlist_maker_android_rodionovmatvey.data.preferences.SearchHistoryStorage
import com.practicum.playlist_maker_android_rodionovmatvey.domain.SearchHistoryRepository

class SearchHistoryRepositoryImpl(
    private val searchHistoryStorage: SearchHistoryStorage
) : SearchHistoryRepository {

    override fun addEntry(word: String) {
        searchHistoryStorage.addEntry(word)
    }

    override suspend fun getEntries(): List<String> {
        return searchHistoryStorage.getEntries()
    }
}