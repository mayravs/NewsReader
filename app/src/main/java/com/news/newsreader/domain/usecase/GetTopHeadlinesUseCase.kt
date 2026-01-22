package com.news.newsreader.domain.usecase

import com.news.newsreader.data.repository.NewsRepo
import javax.inject.Inject

class GetTopHeadlinesUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke() = newsRepo.getTopHeadlines()
}