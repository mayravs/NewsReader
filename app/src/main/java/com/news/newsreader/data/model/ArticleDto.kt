package com.news.newsreader.data.model

import com.news.newsreader.data.local.ArticleEntity

data class NewsResponseDto(
    val articles: List<ArticleDto>
)

data class ArticleDto(
    val source: SourceDto?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class SourceDto(
    val id: String?,
    val name: String?
)

fun NewsResponseDto.toEntityList(): List<ArticleEntity> {
    return this.articles.map { dto ->
        ArticleEntity(
            title = dto.title,
            description = dto.description,
            url = dto.url,
            publishedAt = dto.publishedAt,
            content = dto.content,
            imagePath = dto.urlToImage,
            lastUpdatedAt = System.currentTimeMillis(),
            sourceName = dto.source?.name,
            author = dto.author
        )
    }
}