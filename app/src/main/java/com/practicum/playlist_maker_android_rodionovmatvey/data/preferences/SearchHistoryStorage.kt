package com.practicum.playlist_maker_android_rodionovmatvey.data.preferences

interface SearchHistoryStorage {
    fun addEntry(word: String)

    suspend fun getEntries(): List<String>
}