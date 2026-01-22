package com.news.newsreader.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.news.newsreader.domain.usecase.GetEverythingUseCase
import com.news.newsreader.domain.usecase.GetTopHeadlinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val getEverythingUseCase: GetEverythingUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow<NewsUiState>(NewsUiState.Empty)
    val uiState: StateFlow<NewsUiState> = _uiState

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines() {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            val result = getTopHeadlinesUseCase()
            _uiState.value = result.fold(
                onSuccess = { articles ->
                    if (articles.isEmpty()) NewsUiState.Empty
                    else NewsUiState.Success(articles)
                },
                onFailure = { throwable -> NewsUiState.Error(throwable.localizedMessage ?: "Unknown Error")}
            )
        }
    }

    fun search(searchString: String, fromDate: String? = null, toDate: String? = null) {
        viewModelScope.launch {
            _uiState.value = NewsUiState.Loading
            val result = getEverythingUseCase(searchString, fromDate, toDate)
            _uiState.value = result.fold(
                onSuccess = { articles ->
                    if (articles.isEmpty()) NewsUiState.Empty
                    else NewsUiState.Success(articles)
                },
                onFailure = { throwable -> NewsUiState.Error(throwable.localizedMessage ?: "Unknown Error")}
            )
        }
    }
}