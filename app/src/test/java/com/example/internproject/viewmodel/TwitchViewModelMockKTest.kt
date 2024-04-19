package com.example.internproject.viewmodel

import com.example.internproject.model.Stream
import com.example.internproject.repository.RepositoryInterface
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class TwitchViewModelMockKTest {

    private lateinit var viewModel: TwitchViewModel
    private lateinit var repository: RepositoryInterface
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk<RepositoryInterface>(relaxed = true)
        viewModel = TwitchViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetStreams() {
        val authorizationToken = "authorizationToken"
        val clientId = "clientId"

        coEvery { repository.getStreams(authorizationToken, clientId) } returns flow { emit(listOf<Stream>()) }

        viewModel.getStreams(authorizationToken, clientId)

        verify(exactly = 1) { repository.getStreams(authorizationToken, clientId) }
    }
}
