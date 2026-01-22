package com.news.newsreader.data.repository

import com.news.newsreader.data.remote.NewsApiService
import com.news.newsreader.data.remote.dto.ArticleDto
import com.news.newsreader.domain.model.Article
import javax.inject.Inject

class NewsRepoImpl @Inject constructor(
    private val newsService: NewsApiService
) : NewsRepo {

    private fun mapResponse(dto: ArticleDto): Article {
        return Article(
            sourceName = dto.source?.name,
            author = dto.author,
            title = dto.title,
            description = dto.description,
            url = dto.url,
            imageUrl = dto.urlToImage,
            publishedAt = dto.publishedAt,
            content = dto.content
        )
    }

    override suspend fun getTopHeadlines(country: String): Result<List<Article>> {
        return try {
            val response = newsService.getTopHeadlines(country = country)
            Result.success(response.articles.map { mapResponse(it) })
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }

    override suspend fun getEverything(
        query: String,
        from: String?,
        to: String?
    ): Result<List<Article>> {
        return try {
            val response = newsService.getEverything(
                query = query,
                fromDate = from,
                toDate = to
            )
            Result.success(response.articles.map { mapResponse(it) })
        } catch (t: Throwable) {
            Result.failure(t)
        }
    }
}