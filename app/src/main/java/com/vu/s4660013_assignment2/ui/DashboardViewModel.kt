package com.vu.s4660013_assignment2.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vu.s4660013_assignment2.data.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    fun loadDashboard(keypass: String) {
        viewModelScope.launch {
            repository.getDashboard(keypass)
        }
    }
}
