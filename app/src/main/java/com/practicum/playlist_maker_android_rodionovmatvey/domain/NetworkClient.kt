package com.practicum.playlist_maker_android_rodionovmatvey.domain

import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}