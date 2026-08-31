package com.news.newsreader.ui.details

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.news.newsreader.ui.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Extract the type-safe route from SavedStateHandle
    private val detailsRoute = savedStateHandle.toRoute<Screen.Details>()

    // Decode the URL for the WebView
    val url: String = Uri.decode(detailsRoute.url)
    /*
    We encode the URL because it contains special characters like slashes (/) and colons (:)
    that the Navigation library uses as delimiters for routes. By encoding it, we treat the
    URL as a single safe string segment. We then decode it in the destination ViewModel so
    the WebView receives the original, valid URL.
     */
}