package com.krutika.bookmyshow.data.repository

import com.krutika.bookmyshow.data.remote.models.ShowTimeResponse
import com.krutika.bookmyshow.data.repository.showTimeRepository
import com.speedyy.rider.data.remote.service.ShowtimeService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class showTimeRepositoryImpl @Inject constructor(
    private val showtimeService: ShowtimeService,
) : showTimeRepository {
    override fun getShowTimeList(): Flow<ShowTimeResponse> {
        return showtimeService.getShowTimes().map {
            it
        }
    }

}