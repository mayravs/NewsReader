package com.news.newsreader.ui.favorite

import com.news.newsreader.domain.model.Article

sealed interface FavoritesUiState {
    object Loading: FavoritesUiState

    data class Success(
        val faveArticles: List<Article>
    ): FavoritesUiState

    data class Error(
        val message: String
    ): FavoritesUiState

    object Empty: FavoritesUiState
}