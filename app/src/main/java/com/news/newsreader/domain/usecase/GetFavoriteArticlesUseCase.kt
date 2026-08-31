package com.news.newsreader.domain.usecase

import com.news.newsreader.domain.NewsRepo
import com.news.newsreader.domain.model.Article
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetFavoriteArticlesUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    operator fun invoke(): Flow<List<Article>> {
        return newsRepo.getFavorites()
    }
}