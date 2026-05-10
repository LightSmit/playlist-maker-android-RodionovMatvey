package com.practicum.playlist_maker_android_rodionovmatvey.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.practicum.playlist_maker_android_rodionovmatvey.Creator
import com.practicum.playlist_maker_android_rodionovmatvey.domain.Resource
import com.practicum.playlist_maker_android_rodionovmatvey.domain.SearchHistoryRepository
import com.practicum.playlist_maker_android_rodionovmatvey.domain.TracksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel(
    private val tracksRepository: TracksRepository,
    private val searchHistoryRepository: SearchHistoryRepository
) : ViewModel() {

    private val _searchScreenState = MutableStateFlow<SearchState>(SearchState.Initial)
    val searchScreenState = _searchScreenState.asStateFlow()

    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    val searchHistory = _searchHistory.asStateFlow()

    private val searchQuery = MutableStateFlow("")

    private var searchJob: Job? = null
    private var lastFailedQuery: String = ""

    init {
        loadSearchHistory()

        searchJob = viewModelScope.launch {
            searchQuery
                .debounce(500)
                .distinctUntilChanged()
                .collectLatest { query ->
                    if (query.isBlank()) {
                        _searchScreenState.update { SearchState.Initial }
                        return@collectLatest
                    }

                    doSearch(query)
                }
        }
    }

    fun onSearchTextChanged(query: String) {
        searchQuery.value = query
    }

    fun retrySearch() {
        if (lastFailedQuery.isNotBlank()) {
            viewModelScope.launch {
                doSearch(lastFailedQuery)
            }
        }
    }

    fun clear() {
        searchQuery.value = ""
        _searchScreenState.update { SearchState.Initial }
    }

    fun refreshHistory() {
        loadSearchHistory()
    }

    private fun loadSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            val history = searchHistoryRepository.getEntries()
            _searchHistory.update { history }
        }
    }

    private suspend fun doSearch(query: String) {
        val normalizedQuery = query.trim()

        if (normalizedQuery.isBlank()) {
            _searchScreenState.update { SearchState.Initial }
            return
        }

        _searchScreenState.update { SearchState.Searching }

        val result = withContext(Dispatchers.IO) {
            tracksRepository.searchTracks(normalizedQuery)
        }

        when (result) {
            is Resource.Success -> {
                val tracks = result.data

                searchHistoryRepository.addEntry(normalizedQuery)
                updateHistoryInMemory(normalizedQuery)

                _searchScreenState.update {
                    if (tracks.isEmpty()) {
                        SearchState.Empty
                    } else {
                        SearchState.Success(tracks)
                    }
                }
            }

            is Resource.Error -> {
                lastFailedQuery = normalizedQuery
                _searchScreenState.update {
                    SearchState.Fail(result.message)
                }
            }
        }
    }

    private fun updateHistoryInMemory(query: String) {
        _searchHistory.update { currentHistory ->
            buildList {
                add(query)
                addAll(currentHistory.filter { it != query })
            }.take(MAX_HISTORY_ENTRIES)
        }
    }

    override fun onCleared() {
        searchJob?.cancel()
        super.onCleared()
    }

    companion object {
        private const val MAX_HISTORY_ENTRIES = 10

        fun getViewModelFactory(): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(
                        tracksRepository = Creator.getTracksRepository(),
                        searchHistoryRepository = Creator.getSearchHistoryRepository()
                    ) as T
                }
            }
    }
}