package com.speedyy.rider.data.remote.service

import com.krutika.bookmyshow.data.remote.models.ShowTimeResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET


interface ShowtimeService {
    @GET("movie_showtimes")
    fun getShowTimes(): Flow<ShowTimeResponse>
}