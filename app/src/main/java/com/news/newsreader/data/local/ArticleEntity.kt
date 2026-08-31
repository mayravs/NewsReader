package com.news.newsreader.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.newsreader.domain.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey val url: String,
    val title: String?,
    val description: String?,
    val publishedAt: String?,
    val content: String?,
    val imagePath: String?,
    val lastUpdatedAt: Long,
    val sourceName: String?,
    val author: String?,
    val isFavorite: Boolean = false
)

fun ArticleEntity.toArticleDomain(): Article {
    return Article(
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imagePath,
        publishedAt = publishedAt,
        content = content,
        isFavorite = isFavorite
    )
}

fun List<ArticleEntity>.toDomainList(): List<Article> = map { it.toArticleDomain() }