package com.vu.s4660013_assignment2.data

import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val api: ApiService
) {
    suspend fun login(username: String, password: String) =
        api.login(LoginRequest(username, password))

    suspend fun getDashboard(keypass: String) =
        api.getDashboard(keypass)
}
