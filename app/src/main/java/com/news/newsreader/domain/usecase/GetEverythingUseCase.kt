package com.news.newsreader.domain.usecase

import com.news.newsreader.domain.NewsRepo
import com.news.newsreader.domain.model.Article
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEverythingUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke(): Flow<List<Article>> {
        return newsRepo.getEverything()
    }
}