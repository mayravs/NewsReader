package com.news.newsreader.data.remote

import com.news.newsreader.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "us",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20,
        @Query("q") q: String? = null
    ) : NewsResponseDto

    @GET("/v2/everything")
    suspend fun getEverything(
        @Query("q") query: String,
        @Query("from") fromDate: String? = null,
        @Query("to") toDate: String? = null,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ) : NewsResponseDto
}