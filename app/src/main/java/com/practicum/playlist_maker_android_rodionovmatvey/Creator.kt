package com.practicum.playlist_maker_android_rodionovmatvey

import com.practicum.playlist_maker_android_rodionovmatvey.creator.Storage
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.RetrofitNetworkClient
import com.practicum.playlist_maker_android_rodionovmatvey.data.network.TracksRepositoryImpl
import com.practicum.playlist_maker_android_rodionovmatvey.domain.TracksRepository

object Creator {

    fun getTracksRepository(): TracksRepository {
        val storage = Storage()
        val networkClient = RetrofitNetworkClient(storage)
        return TracksRepositoryImpl(networkClient)
    }
}