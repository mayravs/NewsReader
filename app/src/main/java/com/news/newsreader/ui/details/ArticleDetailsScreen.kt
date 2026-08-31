package com.news.newsreader.ui.details

import android.content.Intent
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.news.newsreader.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsScreen(
    modifier: Modifier = Modifier,
    viewModel: ArticleDetailsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit
){
    ArticleDetailsContent(
        modifier = modifier,
        url = viewModel.url,
        onBackPressed = onBackPressed
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailsContent(
    modifier: Modifier = Modifier,
    url: String,
    onBackPressed: () -> Unit
){
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.article)) },
                navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                tint = Color.Black,
                                contentDescription = "back-button"
                            )
                        }
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_TEXT, url)
                            }
                            context.startActivity(
                                Intent.createChooser(shareIntent, "Share article")
                            )
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Share,
                                tint = Color.Black,
                                contentDescription = "share-button"
                            )
                        }
                    )
                }
            )
        }
    ) { padding ->
        AndroidView(
            modifier = modifier
                .fillMaxSize()
                .padding(padding),
            factory = {
                WebView(it).apply {
                    webViewClient = WebViewClient()
                    settings.javaScriptEnabled = true
                    loadUrl(url)
                }
            }
        )
    }
}

@Preview
@Composable
fun ArticleDetailsScreenPreview(){
    ArticleDetailsContent(
        url = "https://www.google.com",
        onBackPressed = {}
    )
}