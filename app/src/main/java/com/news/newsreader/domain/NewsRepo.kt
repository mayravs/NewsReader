package com.news.newsreader.domain

import com.news.newsreader.data.Result
import com.news.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    fun getTopHeadlines(): Flow<List<Article>>

    fun refreshTopHeadlines(): Flow<Result<Unit>>

    fun getEverything(): Flow<List<Article>>

    fun getArticleByUrl(url: String): Flow<Article>

    suspend fun toggleIsFavorite(url: String, isFavorite: Boolean)

    fun getFavorites(): Flow<List<Article>>
}