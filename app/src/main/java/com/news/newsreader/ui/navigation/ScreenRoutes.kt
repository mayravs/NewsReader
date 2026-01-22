package com.news.newsreader.ui.navigation

sealed class Screen(val route: String) {
    object List: Screen("List")

    object Details: Screen("Details/{url}")
}