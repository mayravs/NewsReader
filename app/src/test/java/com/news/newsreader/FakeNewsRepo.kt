package com.news.newsreader

import com.news.newsreader.data.repository.NewsRepo
import com.news.newsreader.domain.model.Article

class FakeNewsRepo : NewsRepo {

    // Internal state to simulate a database or network cache
    private val fakeArticles = mutableListOf<Article>()

    // Control variables for testing different scenarios
    private var shouldReturnError = false
    private var failureThrowable: Throwable = Exception("Something went wrong")

    fun setShouldReturnError(value: Boolean, cause: Throwable? = null) {
        shouldReturnError = value
        cause?.let { failureThrowable = it }
    }

    fun emitArticles(articles: List<Article>) {
        fakeArticles.clear()
        fakeArticles.addAll(articles)
    }

    override suspend fun getTopHeadlines(country: String): Result<List<Article>> {
        return if (shouldReturnError) {
            Result.failure(failureThrowable)
        } else {
            Result.success(fakeArticles)
        }
    }

    override suspend fun getEverything(
        query: String,
        from: String?,
        to: String?
    ): Result<List<Article>> {
        return if (shouldReturnError) {
            Result.failure(failureThrowable)
        } else {
            // Basic simulation of filtering logic
            val filtered = fakeArticles.filter { it.title?.contains(query, ignoreCase = true) == true }
            Result.success(filtered)
        }
    }
}