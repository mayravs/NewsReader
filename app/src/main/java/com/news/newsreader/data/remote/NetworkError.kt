package com.news.newsreader.data.remote

import com.squareup.moshi.JsonDataException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

sealed class NetworkError {
    object NoInternet : NetworkError()
    object Timeout : NetworkError()
    object ServerError : NetworkError()
    object SerializationError : NetworkError()
    data class Unknown(val message: String?) : NetworkError()
}

fun Throwable.toNetworkError(): NetworkError {
    return when (this) {
        is ConnectException -> NetworkError.NoInternet
        is UnknownHostException -> NetworkError.NoInternet
        is SocketTimeoutException -> NetworkError.Timeout
        is HttpException -> {
            if (this.code() >= 500) NetworkError.ServerError else NetworkError.Unknown(this.message())
        }
        is JsonDataException -> NetworkError.SerializationError
        is IOException -> NetworkError.NoInternet
        else -> NetworkError.Unknown(this.localizedMessage)
    }
}

fun NetworkError.toErrorMessage(): String {
    return when (this) {
        NetworkError.NoInternet -> "No internet connection. Please check your network."
        NetworkError.Timeout -> "The request timed out. Please try again."
        NetworkError.ServerError -> "Server error. We're working on it!"
        NetworkError.SerializationError -> "Data error. Please update the app."
        is NetworkError.Unknown -> this.message ?: "An unexpected error occurred."
    }
}
