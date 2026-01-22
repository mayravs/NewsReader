package com.news.newsreader.data.repository

import com.news.newsreader.domain.model.Article

interface NewsRepo {

    suspend fun getTopHeadlines(country: String = "us"): Result<List<Article>>

    suspend fun getEverything(query: String, from: String? = null, to: String? = null): Result<List<Article>>
}