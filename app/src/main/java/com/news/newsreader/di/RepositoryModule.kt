package com.news.newsreader.di

import com.news.newsreader.domain.NewsRepo
import com.news.newsreader.data.repository.NewsRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    @Singleton
    fun providesNewsRepo(impl: NewsRepoImpl): NewsRepo = impl
}