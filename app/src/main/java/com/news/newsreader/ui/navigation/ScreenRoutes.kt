package com.news.newsreader.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    object List : Screen
    @Serializable
    data class Details(val url: String) : Screen
    @Serializable
    object Favorites : Screen
}