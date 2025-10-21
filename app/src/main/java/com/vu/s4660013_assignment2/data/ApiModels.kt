package com.vu.s4660013_assignment2.data

data class LoginRequest(
    val username: String,
    val password: String
)

data class LoginResponse(
    val keypass: String
)

data class DashboardResponse(
    val entities: List<EntityItem>,
    val entityTotal: Int
)