package com.krutika.bookmyshow.data.repository

import com.krutika.bookmyshow.data.remote.models.ShowTimeResponse
import kotlinx.coroutines.flow.Flow

interface showTimeRepository {
    fun getShowTimeList(): Flow<ShowTimeResponse>
}