package com.practicum.playlist_maker_android_rodionovmatvey.data.network

import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.BaseResponse
import com.practicum.playlist_maker_android_rodionovmatvey.data.dto.TracksSearchRequest
import com.practicum.playlist_maker_android_rodionovmatvey.domain.NetworkClient
import java.io.IOException

class RetrofitNetworkClient(
    private val api: ITunesApiService
) : NetworkClient {

    override suspend fun doRequest(dto: Any): BaseResponse {
        return try {
            when (dto) {
                is TracksSearchRequest -> {
                    api.searchTracks(
                        query = dto.expression,
                        media = "music",
                        entity = "song",
                        limit = 50
                    ).apply {
                        resultCode = 200
                    }
                }

                else -> {
                    BaseResponse().apply {
                        resultCode = 400
                        errorMessage = "Неверный тип запроса"
                    }
                }
            }
        } catch (e: IOException) {
            BaseResponse().apply {
                resultCode = -1
                errorMessage = "Проблемы со связью: ${e.message}"
            }
        } catch (e: Exception) {
            BaseResponse().apply {
                resultCode = -2
                errorMessage = "Ошибка сервера"
            }
        }
    }
}