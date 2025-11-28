package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import com.practicum.playlist_maker_android_rodionovmatvey.creator.Storage
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.BaseResponse
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchRequest
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchResponse
import com.practicum.playlist_maker_android_rodionovmatvey.domain.NetworkClient

class RetrofitNetworkClient(
    private val storage: Storage
) : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {
        val request = dto as TracksSearchRequest
        val searchList = storage.search(request.expression)
        return TracksSearchResponse(searchList).apply {
            resultCode = 200
        }
    }
}