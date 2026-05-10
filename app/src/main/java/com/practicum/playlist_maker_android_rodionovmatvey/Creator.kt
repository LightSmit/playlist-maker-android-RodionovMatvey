package com.practicum.playlist_maker_android_rodionovmatvey

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.SearchHistoryRepositoryImpl
import com.practicum.playlist_maker_android_rodionovmatvey.data.preferences.SearchHistoryPreferences
import com.practicum.playlist_maker_android_rodionovmatvey.domain.SearchHistoryRepository
import java.io.File
import android.content.Context
import androidx.room.Room
import com.practicum.playlist_maker_android_rodionovmatvey.data.db.AppDatabase
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.ITunesApiService
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.PlaylistsRepositoryImpl
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.RetrofitNetworkClient
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.TracksRepositoryImpl
import com.practicum.playlist_maker_android_rodionovmatvey.domain.NetworkClient
import com.practicum.playlist_maker_android_rodionovmatvey.domain.PlaylistsRepository
import com.practicum.playlist_maker_android_rodionovmatvey.domain.TracksRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Creator {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val searchHistoryDataStore by lazy {
        PreferenceDataStoreFactory.create {
            File(appContext.filesDir, "search_history.preferences_pb")
        }
    }

    private val searchHistoryPreferences by lazy {
        SearchHistoryPreferences(searchHistoryDataStore)
    }

    private val searchHistoryRepository by lazy {
        SearchHistoryRepositoryImpl(searchHistoryPreferences)
    }

    fun getSearchHistoryRepository(): SearchHistoryRepository = searchHistoryRepository

    private val roomDatabase by lazy {
        Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "playlist_maker_database.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ITunesApiService::class.java)
    }

    private val networkClient: NetworkClient by lazy {
        RetrofitNetworkClient(api = apiService)
    }

    private val tracksRepository by lazy {
        TracksRepositoryImpl(
            networkClient = networkClient,
            trackDao = roomDatabase.trackDao()
        )
    }

    private val playlistsRepository by lazy {
        PlaylistsRepositoryImpl(
            playlistDao = roomDatabase.playlistDao(),
            trackDao = roomDatabase.trackDao()
        )
    }

    fun getTracksRepository(): TracksRepository = tracksRepository

    fun getPlaylistsRepository(): PlaylistsRepository = playlistsRepository
}