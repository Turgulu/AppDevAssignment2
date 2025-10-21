package com.vu.s4660013_assignment2.ui

import com.vu.s4660013_assignment2.data.ApiRepository
import com.vu.s4660013_assignment2.data.DashboardResponse
import com.vu.s4660013_assignment2.data.EntityItem
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var mockRepository: ApiRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set up the main dispatcher for testing
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        viewModel = DashboardViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after tests
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchDashboardDataSuccessWhenRepositorySucceeds() = runTest(testDispatcher) {
        // Given
        val keypass = "test-topic"
        val expectedEntities = listOf(
            EntityItem(id = 1, name = "Entity 1", description = "Description 1", rating = 4.5f),
            EntityItem(id = 2, name = "Entity 2", description = "Description 2", rating = 3.8f)
        )
        val dashboardResponse = DashboardResponse(expectedEntities, 2)

        coEvery { mockRepository.getDashboard(keypass) } returns Result.success(dashboardResponse)

        // When
        viewModel.fetchDashboardData(keypass)
        advanceUntilIdle() // Wait for coroutines to complete

        // Then
        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Success)
        assertEquals(expectedEntities, (state as DashboardState.Success).entities)
    }

    @Test
    fun testFetchDashboardDataErrorWhenRepositoryFails() = runTest(testDispatcher) {
        // Given
        val keypass = "test-topic"
        val errorMessage = "Network error"

        coEvery { mockRepository.getDashboard(keypass) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.fetchDashboardData(keypass)
        advanceUntilIdle() // Wait for coroutines to complete

        // Then
        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Error)
        assertEquals(errorMessage, (state as DashboardState.Error).message)
    }

    @Test
    fun testInitialStateShouldBeLoading() {
        // When
        val initialState = viewModel.dashboardState.value

        // Then
        assertTrue(initialState is DashboardState.Loading)
    }
}