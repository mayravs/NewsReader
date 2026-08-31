package com.news.newsreader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArticleEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao
}