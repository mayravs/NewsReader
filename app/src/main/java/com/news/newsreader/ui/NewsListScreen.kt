package com.news.newsreader.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.news.newsreader.domain.model.Article

@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    onArticleClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    when (uiState) {
        is NewsUiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    CircularProgressIndicator()
                }
            )
        }
        is NewsUiState.Error -> {
            val msg = (uiState as NewsUiState.Error).message
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Error: $msg")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { viewModel.fetchTopHeadlines() }
                ) {
                    Text(text = "Retry")
                }
            }
        }
        is NewsUiState.Empty -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
                content = {
                    Text(text = "No articles found")
                }
            )
        }
        is NewsUiState.Success -> {
            val articles = (uiState as NewsUiState.Success).articles
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) { 
                items(articles) { article ->
                    NewsRow(
                        article = article,
                        onClick = {
                            article.url?.let { onArticleClick(it) }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NewsRow(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        if (article.imageUrl != null) {
            /* TODO: Use coil for images */
        }
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = article.title ?: "No title"
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.publishedAt ?: "-",
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun NewsListScreenPreview() {
    NewsListScreen(
        onArticleClick = {}
    )
}