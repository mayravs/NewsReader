package com.news.newsreader.domain.usecase

import com.news.newsreader.domain.NewsRepo
import jakarta.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke(url: String, isFavorite: Boolean) {
        return newsRepo.toggleIsFavorite(url = url, isFavorite = isFavorite)
    }
}