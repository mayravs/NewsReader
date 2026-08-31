package com.news.newsreader.data

import com.news.newsreader.data.remote.NetworkError
import com.news.newsreader.data.remote.toNetworkError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()

    data class Error(val exception: NetworkError) : Result<Nothing>()

    object Loading : Result<Nothing>()
}

fun <T> safeApiCall(call: suspend () -> T): Flow<Result<T>> = flow {
    // 1. Tell the UI we are loading
    emit(Result.Loading)

    try {
        // 2. Try the actual network call
        val response = call()
        emit(Result.Success(response))
    } catch (e: Exception) {
        // 3. If it fails, use our mapper and emit the error
        emit(Result.Error(e.toNetworkError()))
    }
}.flowOn(Dispatchers.IO) // 4. Ensure this always runs on a background thread