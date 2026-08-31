package com.news.newsreader.data.repository

import android.util.Log
import com.news.newsreader.data.Result
import com.news.newsreader.data.safeApiCall
import com.news.newsreader.data.local.ArticleDao
import com.news.newsreader.data.local.toArticleDomain
import com.news.newsreader.data.local.toDomainList
import com.news.newsreader.data.model.toEntityList
import com.news.newsreader.data.remote.NewsApiService
import com.news.newsreader.domain.NewsRepo
import com.news.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
* The Repository calls the API
* Then saves the data immediately to the Room database
* It doesn't take the API data to the UI, it just updates the database
 */
class NewsRepoImpl @Inject constructor(
    private val newsService: NewsApiService,
    private val articleDao: ArticleDao
) : NewsRepo {

    override fun getTopHeadlines(): Flow<List<Article>> {
        return articleDao.getAllArticles().map { entities ->
            entities.toDomainList()
        }
    }

    override fun refreshTopHeadlines(): Flow<Result<Unit>> {
        return safeApiCall {
            val response = newsService.getTopHeadlines()
            val entities = response.toEntityList()
            articleDao.upsertArticles(entities)
        }
    }

    /* TODO: Implement this method properly */
    override fun getEverything(): Flow<List<Article>> {
        return articleDao.getAllArticles().map { entities ->
            entities.toDomainList()
        }
    }

    override fun getArticleByUrl(url: String): Flow<Article> {
        return articleDao.getArticleByUrl(url).map { entity ->
            entity.toArticleDomain()
        }
    }

    override suspend fun toggleIsFavorite(url: String, isFavorite: Boolean) {
        articleDao.updateIsFavorite(
            isFavorite = isFavorite,
            url = url
        )
    }

    override fun getFavorites(): Flow<List<Article>> {
        return articleDao.getFavoriteArticles().map { entities ->
            entities.toDomainList()
        }
    }
}