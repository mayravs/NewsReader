package com.news.newsreader.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

/**
* The database holds all the data
* Since the Repository uses a Flow to read from the database,
* the database "broadcasts" a message when its data changes
 */
@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(articles: List<ArticleEntity>)

    @Query("DELETE FROM articles")
    suspend fun clearAllArticles()

    @Query("SELECT * FROM articles ORDER BY publishedAt DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Query("SELECT * FROM articles WHERE url = :url")
    fun getArticleByUrl(url: String): Flow<ArticleEntity>

    @Query("UPDATE articles SET isFavorite = :isFavorite WHERE url = :url")
    suspend fun updateIsFavorite(isFavorite: Boolean, url: String)

    @Query("SELECT url FROM articles WHERE isFavorite = 1")
    suspend fun getFavoriteUrls(): List<String>

    @Transaction
    suspend fun upsertArticles(articles: List<ArticleEntity>) {
        val favoriteUrls = getFavoriteUrls().toSet()
        val mergedArticles = articles.map { article ->
            if (favoriteUrls.contains(article.url)) {
                article.copy(isFavorite = true)
            } else {
                article
            }
        }
        insertAll(mergedArticles)
    }

    @Query("SELECT * FROM articles WHERE isFavorite = 1")
    fun getFavoriteArticles(): Flow<List<ArticleEntity>>
}