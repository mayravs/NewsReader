package com.news.newsreader.ui.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.news.newsreader.ui.details.ArticleDetailsScreen
import com.news.newsreader.ui.favorite.FavoritesScreen
import com.news.newsreader.ui.list.NewsListScreen

fun NavGraphBuilder.appNavGraph(
    navController: NavController,
) {
    composable<Screen.List> {
        NewsListScreen(
            onArticleClick = { url ->
                val encodeUrl = Uri.encode(url)
                navController.navigate(Screen.Details(encodeUrl))
            },
            onFavoritesClick = { navController.navigate(Screen.Favorites) }
        )
    }

    composable<Screen.Details> {
        ArticleDetailsScreen(
            onBackPressed = { navController.popBackStack() }
        )
    }

    composable<Screen.Favorites> {
        FavoritesScreen(
            onBackPressed = { navController.popBackStack() },
            onArticleClick = { url ->
                val encodeUrl = Uri.encode(url)
                navController.navigate(Screen.Details(encodeUrl))
            }
        )
    }
}