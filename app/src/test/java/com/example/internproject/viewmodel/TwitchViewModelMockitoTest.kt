package com.example.internproject.viewmodel

import com.example.internproject.repository.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class TwitchViewModelMockitoTest {

    private lateinit var viewModel: TwitchViewModel
    private lateinit var repository : RepositoryInterface
    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        repository = mock(RepositoryInterface::class.java)
        viewModel = TwitchViewModel(repository)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testGetFollowedStreams() {
        val authorizationToken = "authorizationToken"
        val clientId = "clientId"
        val userId = "userId"

        `when`(repository.getFollowedStreams(authorizationToken, clientId, userId)).thenReturn(flow { emit(listOf()) })
        viewModel.getFollowedStreams(authorizationToken, clientId, userId)

        verify(repository, times(1)).getFollowedStreams(authorizationToken, clientId, userId)
    }
}