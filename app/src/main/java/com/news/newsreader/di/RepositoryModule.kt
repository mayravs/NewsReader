package com.news.newsreader.di

import com.news.newsreader.data.repository.NewsRepo
import com.news.newsreader.data.repository.NewsRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindNewsRepository(impl: NewsRepoImpl): NewsRepo
}