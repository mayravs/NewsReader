package com.news.newsreader.data.repository

import android.util.Log
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

class NewsRepoImpl @Inject constructor(
    private val newsService: NewsApiService,
    private val articleDao: ArticleDao
) : NewsRepo {

    override suspend fun getTopHeadlines(): Flow<List<Article>> {
        return articleDao.getAllArticles().map { entities ->
            entities.toDomainList()
        }
    }

    override suspend fun refreshTopHeadlines() {
        try {
            val response = newsService.getTopHeadlines()
            val entities = response.toEntityList()
            articleDao.insertAll(entities)
        } catch (e: Exception) {
            Log.e("Error","Error: $e")
        }
    }

    /* TODO: Implement this method properly */
    override suspend fun getEverything(): Flow<List<Article>> {
        return articleDao.getAllArticles().map { entities ->
            entities.toDomainList()
        }
    }

    override fun getArticleById(id: Int): Flow<Article> {
        return articleDao.getArticleById(id).map { entity ->
            entity.toArticleDomain()
        }
    }
}