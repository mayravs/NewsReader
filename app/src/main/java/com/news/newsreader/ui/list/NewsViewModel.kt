package com.news.newsreader.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsreader.data.Result
import com.news.newsreader.data.remote.toErrorMessage
import com.news.newsreader.domain.model.Article
import com.news.newsreader.domain.usecase.GetTopHeadlinesUseCase
import com.news.newsreader.domain.usecase.RefreshTopHeadlinesUseCase
import com.news.newsreader.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
* The ViewModel collects the data from the Flow (COLD) and pushes it into a StateFlow (HOT) like the uiState
* The ViewModel is a long-lived collector, so when the Repository saves new data into the database,
* the Flow triggers, and the ViewModel automatically gets the updated data
 */
@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val refreshTopHeadlinesUseCase: RefreshTopHeadlinesUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Loading)
    val uiState: StateFlow<NewsUiState> = _uiState.asStateFlow()

    init {
        fetchTopHeadlines()
        refreshTopHeadlines()
    }

    private fun fetchTopHeadlines() {
        viewModelScope.launch {
            getTopHeadlinesUseCase()
                .catch { e ->
                    _uiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
                }
                .collect { articles ->
                    if (articles.isNotEmpty()) {
                        _uiState.value = NewsUiState.Success(articles = articles)
                    } else {
                        _uiState.value = NewsUiState.Empty
                    }
                }
        }
    }

    fun refreshTopHeadlines() {
        viewModelScope.launch {
            refreshTopHeadlinesUseCase().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        if (_uiState.value !is NewsUiState.Success) {
                            _uiState.value = NewsUiState.Loading
                        }
                    }
                    is Result.Success -> {
                        // fetchTopHeadlines() collects from database and updates Success
                    }
                    is Result.Error -> {
                        if (_uiState.value !is NewsUiState.Success) {
                            _uiState.value = NewsUiState.Error(
                                message = result.exception.toErrorMessage()
                            )
                        }
                    }
                }
            }
        }
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