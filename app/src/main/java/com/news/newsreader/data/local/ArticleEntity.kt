package com.news.newsreader.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.news.newsreader.domain.model.Article

@Entity(tableName = "articles")
data class ArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String?,
    val description: String?,
    val url: String?,
    val publishedAt: String?,
    val content: String?,
    val imagePath: String?,
    val lastUpdatedAt: Long,
    val sourceName: String?,
    val author: String?
)

fun ArticleEntity.toArticleDomain(): Article {
    return Article(
        id = id,
        sourceName = sourceName,
        author = author,
        title = title,
        description = description,
        url = url,
        imageUrl = imagePath,
        publishedAt = publishedAt,
        content = content
    )
}

fun List<ArticleEntity>.toDomainList(): List<Article> = map { it.toArticleDomain() }