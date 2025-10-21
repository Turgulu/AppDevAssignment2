package com.vu.s4660013_assignment2.ui

import com.vu.s4660013_assignment2.data.ApiRepository
import com.vu.s4660013_assignment2.data.LoginResponse
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
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var mockRepository: ApiRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Set up the main dispatcher for testing
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk()
        viewModel = LoginViewModel(mockRepository)
    }

    @After
    fun tearDown() {
        // Reset the main dispatcher after tests
        Dispatchers.resetMain()
    }

    @Test
    fun testLoginSuccessWhenRepositoryReturnsValidKeypass() = runTest(testDispatcher) {
        // Given
        val username = "Efe"
        val password = "4660013"
        val expectedKeypass = "test-topic"
        val loginResponse = LoginResponse(expectedKeypass)

        coEvery { mockRepository.login(username, password) } returns Result.success(loginResponse)

        // When
        viewModel.login(username, password)
        advanceUntilIdle() // Wait for coroutines to complete

        // Then
        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Success)
        assertEquals(expectedKeypass, (state as LoginState.Success).keypass)
    }

    @Test
    fun testLoginErrorWhenRepositoryFails() = runTest(testDispatcher) {
        // Given
        val username = "Efe"
        val password = "4660013"
        val errorMessage = "Login failed"

        coEvery { mockRepository.login(username, password) } returns Result.failure(Exception(errorMessage))

        // When
        viewModel.login(username, password)
        advanceUntilIdle() // Wait for coroutines to complete

        // Then
        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
        assertEquals(errorMessage, (state as LoginState.Error).message)
    }

    @Test
    fun testInitialStateShouldBeIdle() {
        // When
        val initialState = viewModel.loginState.value

        // Then
        assertTrue(initialState is LoginState.Idle)
    }
}