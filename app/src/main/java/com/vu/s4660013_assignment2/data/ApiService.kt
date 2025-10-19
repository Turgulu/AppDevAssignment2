package com.vu.s4660013_assignment2.data

import retrofit2.Response
import retrofit2.http.*

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

data class EntityItem(
    val property1: String,
    val property2: String,
    val description: String
)

interface ApiService {
    @POST("footscray/auth")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("dashboard/{keypass}")
    suspend fun getDashboard(@Path("keypass") keypass: String): Response<DashboardResponse>
}