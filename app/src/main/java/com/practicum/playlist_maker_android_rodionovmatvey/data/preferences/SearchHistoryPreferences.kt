package com.practicum.playlist_maker_android_rodionovmatvey.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SearchHistoryPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope = CoroutineScope(
        CoroutineName("search-history-preferences") + SupervisorJob()
    )
) {

    fun addEntry(word: String) {
        val normalizedWord = word.trim()

        if (normalizedWord.isEmpty()) {
            return
        }

        coroutineScope.launch {
            dataStore.edit { preferences ->
                val historyString = preferences[SEARCH_HISTORY_KEY].orEmpty()
                val history = if (historyString.isNotEmpty()) {
                    historyString.split(SEPARATOR).toMutableList()
                } else {
                    mutableListOf()
                }

                history.remove(normalizedWord)
                history.add(0, normalizedWord)

                val updatedHistory = history
                    .take(MAX_ENTRIES)
                    .joinToString(SEPARATOR)

                preferences[SEARCH_HISTORY_KEY] = updatedHistory
            }
        }
    }

    suspend fun getEntries(): List<String> {
        val preferences = dataStore.data.first()
        val historyString = preferences[SEARCH_HISTORY_KEY].orEmpty()

        if (historyString.isEmpty()) {
            return emptyList()
        }

        return historyString.split(SEPARATOR)
    }

    companion object {
        private const val MAX_ENTRIES = 10
        private const val SEPARATOR = ","
        private val SEARCH_HISTORY_KEY = stringPreferencesKey("search_history")
    }
}