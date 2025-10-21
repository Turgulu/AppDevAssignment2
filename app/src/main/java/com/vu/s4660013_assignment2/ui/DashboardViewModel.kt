package com.vu.s4660013_assignment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vu.s4660013_assignment2.data.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.onFailure
import kotlin.onSuccess

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val apiRepository: ApiRepository
) : ViewModel() {

    private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.Loading)
    val dashboardState: StateFlow<DashboardState> = _dashboardState.asStateFlow()

    fun fetchDashboardData(keypass: String) {
        viewModelScope.launch {
            _dashboardState.value = DashboardState.Loading
            try {
                val result = apiRepository.getDashboard(keypass)
                result.onSuccess { dashboardResponse ->
                    _dashboardState.value = DashboardState.Success(dashboardResponse.entities)
                }.onFailure { exception ->
                    _dashboardState.value = DashboardState.Error(exception.message ?: "Unknown error")
                }
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error(e.message ?: "Network error")
            }
        }
    }
}

sealed class DashboardState {
    object Loading : DashboardState()
    data class Success(val entities: List<com.vu.s4660013_assignment2.data.EntityItem>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}