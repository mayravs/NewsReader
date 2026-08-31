package com.news.newsreader

import com.news.newsreader.data.Result
import com.news.newsreader.data.remote.NetworkError
import com.news.newsreader.domain.NewsRepo
import com.news.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeNewsRepo : NewsRepo {

    // Reactive state to simulate a database
    private val _articlesFlow = MutableStateFlow<List<Article>>(emptyList())
    private var shouldReturnError = false
    private var exception: Exception = Exception("Fake error")

    fun emitArticles(articles: List<Article>) {
        _articlesFlow.value = articles
    }

    fun setShouldReturnError(shouldReturn: Boolean, e: Exception = Exception("Fake error")) {
        shouldReturnError = shouldReturn
        exception = e
    }

    override fun getTopHeadlines(): Flow<List<Article>> {
        return if (shouldReturnError) {
            flow { throw exception }
        } else {
            _articlesFlow
        }
    }

    override fun refreshTopHeadlines(): Flow<Result<Unit>> {
        return if (shouldReturnError) {
            flow { emit(Result.Error(NetworkError.Unknown(exception.message))) }
        } else {
            flow { emit(Result.Success(Unit)) }
        }
    }

    override fun getEverything(): Flow<List<Article>> {
        return if (shouldReturnError) {
            flow { throw exception }
        } else {
            _articlesFlow
        }
    }

    override fun getArticleByUrl(url: String): Flow<Article> {
        return if (shouldReturnError) {
            flow { throw exception }
        } else {
            _articlesFlow.map { list ->
                list.first { it.url == url }
            }
        }
    }

    override suspend fun toggleIsFavorite(url: String, isFavorite: Boolean) {
        if (shouldReturnError) throw exception
        _articlesFlow.update { currentArticles ->
            currentArticles.map { article ->
                if (article.url == url) {
                    article.copy(isFavorite = isFavorite)
                } else {
                    article
                }
            }
        }
    }

    override fun getFavorites(): Flow<List<Article>> {
        return if (shouldReturnError) {
            flow { throw exception }
        } else {
            _articlesFlow.map { list ->
                list.filter { it.isFavorite }
            }
        }
    }
}