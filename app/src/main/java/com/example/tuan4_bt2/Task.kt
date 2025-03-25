package com.example.tuan4_bt2

import java.util.Date


data class Task(
    val id: Int,
    val title: String,
    val desImageURL: String,
    val description: String,
    val status: String,
    val priority: String,
    val category: String,
    val dueDate: String,
    val createdAt: String,
    val updatedAt: String,
    val subtasks: List<Subtask>
)

data class Subtask(
    val id: Int,
    val title: String,
    val isCompleted: Boolean
)
