package com.news.newsreader.domain.usecase

import com.news.newsreader.domain.NewsRepo
import javax.inject.Inject

class RefreshTopHeadlinesUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke() {
        return newsRepo.refreshTopHeadlines()
    }
}