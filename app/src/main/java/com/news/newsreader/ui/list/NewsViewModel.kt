package com.news.newsreader.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsreader.domain.usecase.GetTopHeadlinesUseCase
import com.news.newsreader.domain.usecase.RefreshTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val refreshTopHeadlinesUseCase: RefreshTopHeadlinesUseCase
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
                    }
                }
        }
    }

    fun refreshTopHeadlines() {
        viewModelScope.launch {
            try {
                refreshTopHeadlinesUseCase()
            } catch (e: Exception) {
                if (_uiState.value is NewsUiState.Loading) {
                    _uiState.value = NewsUiState.Error(e.message ?: "Unknown Error")
                }
            }
        }
    }
}