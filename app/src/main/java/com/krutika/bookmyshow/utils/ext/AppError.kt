package com.krutika.bookmyshow.utils.ext

import androidx.annotation.Keep

@Keep
sealed class AppError(cause: Throwable?) : RuntimeException(cause) {

    sealed class ApiException(cause: Throwable?) : AppError(cause) {
        class NetworkException(cause: Throwable?) : ApiException(cause)
        class ServerException(cause: Throwable?) : ApiException(cause)
        class SessionNotFoundException(cause: Throwable?) : AppError(cause)
        class UnknownException(cause: Throwable?) : AppError(cause)
        class NetworkNotConnectedException(cause: Throwable?) : AppError(cause)
        class BadRequestException(cause: Throwable?) : AppError(cause)
    }

    class UnknownException(cause: Throwable?) : AppError(cause)
}
