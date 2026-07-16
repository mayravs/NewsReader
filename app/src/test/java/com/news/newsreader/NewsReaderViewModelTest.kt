package com.news.newsreader

import com.news.newsreader.domain.model.Article
import com.news.newsreader.domain.usecase.GetEverythingUseCase
import com.news.newsreader.domain.usecase.GetTopHeadlinesUseCase
import com.news.newsreader.ui.list.NewsUiState
import com.news.newsreader.ui.list.NewsViewModel
import com.news.newsreader.utils.MainDispatcherRule
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class NewsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: NewsViewModel
    private lateinit var fakeRepo: FakeNewsRepo
    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    private lateinit var getEverythingUseCase: GetEverythingUseCase

    @Before
    fun setup() {
        fakeRepo = FakeNewsRepo()
        // Inject the fake into real use cases
        getTopHeadlinesUseCase = GetTopHeadlinesUseCase(fakeRepo)
        getEverythingUseCase = GetEverythingUseCase(fakeRepo)

        // Note: fetchTopHeadlines is called in init,
        // so the VM will trigger logic immediately upon instantiation.
    }

    @Test
    fun `fetchTopHeadlines updates state to Success when repo returns data`() = runTest {
        // Arrange
        val testArticles = listOf(Article(
            id = 1,
            sourceName = "Gizmodo.com",
            author = "Mike Pearl",
            title = "Bitcoin Mining is Being Used to Offset Heating Costs in Greenhouses and Homes",
            description = "Of course, bitcoin critics would argue there is nothing truly gained here in terms of energy efficiency.",
            url = "https://gizmodo.com/bitcoin-mining-is-being-used-to-offset-heating-costs-in-greenhouses-and-homes-2000708684",
            imageUrl = "https://gizmodo.com/app/uploads/2026/01/btc-heat-1200x675.jpg",
            publishedAt = "2026-01-11T21:10:17Z",
            content = "One of the side effects of the energy-intensive process of bitcoin mining is the excess heat that is created by the involved hardware devices. Miners have to prove that theyve expended energy on comp… [+4666 chars]"
        ))
        fakeRepo.emitArticles(testArticles)

        // Act
        viewModel = NewsViewModel(getTopHeadlinesUseCase, getEverythingUseCase)

        // Assert
        assertEquals(NewsUiState.Success(testArticles), viewModel.uiState.value)
    }

    @Test
    fun `fetchTopHeadlines updates state to Error when repo fails`() = runTest {
        // Arrange
        val errorMessage = "No Internet"
        fakeRepo.setShouldReturnError(true, Exception(errorMessage))

        // Act
        viewModel = NewsViewModel(getTopHeadlinesUseCase, getEverythingUseCase)

        // Assert
        val state = viewModel.uiState.value
        assert(state is NewsUiState.Error)
        assertEquals(errorMessage, (state as NewsUiState.Error).message)
    }
}