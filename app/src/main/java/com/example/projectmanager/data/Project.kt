package com.example.projectmanager.data

data class Project(
    val id: Int,
    val name: String,
    val description: String,
    val progress: Float = 0f
)