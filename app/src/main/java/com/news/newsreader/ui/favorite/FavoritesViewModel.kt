package com.news.newsreader.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsreader.domain.model.Article
import com.news.newsreader.domain.usecase.GetFavoriteArticlesUseCase
import com.news.newsreader.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getFavoriteArticlesUseCase: GetFavoriteArticlesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
): ViewModel() {
    private val _uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)
    // StateFlow is hot and always holds the latest value, requires an initial value, never completes and only emits when the value actually changes
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()
    // StateFlow is the standard way to expose UI state from a ViewModel

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            getFavoriteArticlesUseCase()
                .catch { e ->
                    _uiState.value = FavoritesUiState.Error(message = e.message ?: "Unknown Error")
                }
                .collect { articles ->
                    if (articles.isNotEmpty()) {
                        _uiState.value = FavoritesUiState.Success(faveArticles = articles)
                    } else {
                        _uiState.value = FavoritesUiState.Empty
                    }
                }

        }
    }

    fun retry() {
        getFavorites()
    }

    fun toggleFavorite(article: Article) {
        viewModelScope.launch {
            toggleFavoriteUseCase(
                url = article.url,
                isFavorite = !article.isFavorite
            )
        }
    }
}