package com.practicum.playlist_maker_android_rodionovmatvey.domain

sealed class Resource<T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val message: String) : Resource<T>()
}