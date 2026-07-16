package com.news.newsreader.ui.navigation

import android.net.Uri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.news.newsreader.ui.details.ArticleDetailsScreen
import com.news.newsreader.ui.list.NewsListScreen

fun NavGraphBuilder.appNavGraph(
    navController: NavController,
) {
    composable(Screen.List.route) {
        NewsListScreen(
            onArticleClick = { url ->
                val encodeUrl = Uri.encode(url)
                navController.navigate("${Screen.Details}/$encodeUrl")
            }
        )
    }

    composable(
        route = "${Screen.Details}/{url}",
        arguments = listOf(
            navArgument("url") {
                type = NavType.StringType
            }
        )
    ) { backStackEntry ->
        val url = backStackEntry.arguments?.getString("url") ?: ""
        ArticleDetailsScreen(
            url = Uri.decode(url),
            onBackPressed = { navController.popBackStack() }
        )
    }
}