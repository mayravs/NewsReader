package com.news.newsreader.ui.list

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.news.newsreader.R
import com.news.newsreader.domain.model.Article
import com.news.newsreader.ui.DateTimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsListScreen(
    modifier: Modifier = Modifier,
    viewModel: NewsViewModel = hiltViewModel(),
    onArticleClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier,
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Latest Headlines",
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.news_reader_logo),
                        contentDescription = "news-reader-logo",
                        modifier = Modifier.size(50.dp)
                    )
                },
                colors = TopAppBarColors(
                    containerColor = Color(0xFF26456d),
                    scrolledContainerColor = Color(0xFF26456d),
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.Unspecified
                )
            )
        }
    ) { paddingValues ->
        when (uiState) {
            is NewsUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
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
                        onClick = { viewModel.refreshTopHeadlines() }
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
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsRow(
    modifier: Modifier = Modifier,
    article: Article,
    onClick: () -> Unit,
) {
    Surface(
        modifier = modifier.padding(all = 4.dp),
        shape = RoundedCornerShape(size = 8.dp),
        color = Color.White,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
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
                    text = DateTimeUtils.formatArticleDate(article.publishedAt ?: "-"),
                    maxLines = 1
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun NewsRowPreview() {
    NewsRow(
        article = Article(
            sourceName = "Gizmodo.com",
            author = "Mike Pearl",
            title = "Bitcoin Mining is Being Used to Offset Heating Costs in Greenhouses and Homes",
            description = "Of course, bitcoin critics would argue there is nothing truly gained here in terms of energy efficiency.",
            url = "https://gizmodo.com/bitcoin-mining-is-being-used-to-offset-heating-costs-in-greenhouses-and-homes-2000708684",
            imageUrl = "https://gizmodo.com/app/uploads/2026/01/btc-heat-1200x675.jpg",
            publishedAt = "2026-01-11T21:10:17Z",
            content = "One of the side effects of the energy-intensive process of bitcoin mining is the excess heat that is created by the involved hardware devices. Miners have to prove that theyve expended energy on comp… [+4666 chars]"
        ),
        onClick = {}
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NewsListScreenPreview() {
    NewsListScreen(
        onArticleClick = {}
    )
}