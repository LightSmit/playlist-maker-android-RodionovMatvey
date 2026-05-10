package com.practicum.playlist_maker_android_rodionovmatvey.domain

interface SearchHistoryRepository {
    fun addEntry(word: String)

    suspend fun getEntries(): List<String>
}