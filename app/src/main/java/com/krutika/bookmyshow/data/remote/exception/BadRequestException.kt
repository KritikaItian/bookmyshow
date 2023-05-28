package com.speedyy.rider.data.remote.exception

import com.krutika.bookmyshow.utils.ext.ApiError


class BadRequestException(val apiError: ApiError) : Throwable(apiError.errorMessage())
