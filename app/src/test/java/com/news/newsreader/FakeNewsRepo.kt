package com.news.newsreader

import com.news.newsreader.domain.NewsRepo
import com.news.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class FakeNewsRepo : NewsRepo {

    // Internal state to simulate a database or network cache
    private val fakeArticles = mutableListOf<Article>()

    fun emitArticles(articles: List<Article>) {
        fakeArticles.clear()
        fakeArticles.addAll(articles)
    }

    override suspend fun getTopHeadlines(): Flow<List<Article>> {
        return flow { emit(fakeArticles) }
    }

    override suspend fun refreshTopHeadlines() {
        // No-op for fake
    }

    override suspend fun getEverything(): Flow<List<Article>> {
        return flow { emit(fakeArticles) }
    }

    override fun getArticleById(id: Int): Flow<Article> {
        return flow { emit(fakeArticles) }.map { list ->
            list.first { it.id == id }
        }
    }
}