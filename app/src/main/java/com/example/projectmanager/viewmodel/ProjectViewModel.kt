
package com.example.projectmanager.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.projectmanager.data.Project

class ProjectViewModel : ViewModel() {

    private val _projects = MutableStateFlow(
        listOf(
            Project(1, "Мобільний додаток", "iOS/Android розробка", 75f),
            Project(2, "Веб-сайт", "Корпоративний", 50f),
            Project(3, "База даних", "Дизайн та реалізація", 90f)
        )
    )

    val projects: StateFlow<List<Project>> = _projects.asStateFlow()

    private var nextId = 4

    fun addProject(name: String, description: String) {
        if (name.isBlank()) return

        val newProject = Project(
            id = nextId++,
            name = name.trim(),
            description = description.trim(),
            progress = 0f
        )

        _projects.value = _projects.value + newProject
    }

    fun deleteProject(id: Int) {
        _projects.value = _projects.value.filterNot {
            it.id == id
        }
    }

    fun updateProgress(id: Int, progress: Float) {
        _projects.value = _projects.value.map { project ->
            if (project.id == id) {
                project.copy(progress = progress)
            } else {
                project
            }
        }
    }

    fun updateProject(
        id: Int,
        name: String,
        description: String
    ) {
        _projects.value = _projects.value.map { project ->
            if (project.id == id) {
                project.copy(
                    name = name.trim(),
                    description = description.trim()
                )
            } else {
                project
            }
        }
    }

    fun getProjectById(id: Int): Project? {
        return _projects.value.find { it.id == id }
    }
}
