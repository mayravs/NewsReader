package com.news.newsreader.domain.usecase

import com.news.newsreader.data.Result
import com.news.newsreader.domain.NewsRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RefreshTopHeadlinesUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    operator fun invoke(): Flow<Result<Unit>> {
        return newsRepo.refreshTopHeadlines()
    }
}