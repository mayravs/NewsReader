package com.news.newsreader.ui

import com.news.newsreader.domain.model.Article

sealed interface NewsUiState {
    object Loading : NewsUiState

    data class Success(
        val articles: List<Article>
    ) : NewsUiState

    data class Error(
        val message: String
    ) : NewsUiState

    object Empty: NewsUiState
}