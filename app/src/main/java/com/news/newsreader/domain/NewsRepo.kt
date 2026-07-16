package com.news.newsreader.domain

import com.news.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepo {

    suspend fun getTopHeadlines(): Flow<List<Article>>

    suspend fun refreshTopHeadlines()

    suspend fun getEverything(): Flow<List<Article>>

    fun getArticleById(id: Int): Flow<Article>
}