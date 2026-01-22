package com.news.newsreader.domain.usecase

import com.news.newsreader.data.repository.NewsRepo
import javax.inject.Inject

class GetEverythingUseCase @Inject constructor(
    private val newsRepo: NewsRepo
) {
    suspend operator fun invoke(
        query: String,
        fromDate: String?,
        toDate: String?
    ) = newsRepo.getEverything(
        query = query,
        from = fromDate,
        to = toDate
    )
}