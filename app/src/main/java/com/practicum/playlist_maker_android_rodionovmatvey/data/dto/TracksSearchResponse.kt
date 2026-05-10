package com.practicum.playlist_maker_android_rodionovmatvey.data.dto

data class TracksSearchResponse(
    val resultCount: Int? = 0,
    val results: List<TrackDto>? = emptyList()
) : BaseResponse()