package com.example.tuan4_bt2
import com.example.tuan4_bt2.Task
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("tasks") // Đổi từ "task/1" thành "tasks" để lấy danh sách
    suspend fun getTasks(): ApiResponseList
    @GET("tasks/{id}") // Dùng id thay vì title
    suspend fun getTaskDetail(@Path("id") id: String): ApiResponse<Task>
}

data class ApiResponse<T>(
    val isSuccess: Boolean,
    val message: String,
    val data: T
)



data class ApiResponseList(
    val isSuccess: Boolean,
    val message: String,
    val data: List<Task>
)