
package com.example.projectmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.projectmanager.ui.screens.AddProjectScreen
import com.example.projectmanager.ui.screens.DetailsProjectScreen
import com.example.projectmanager.ui.screens.ProjectsListScreen
import com.example.projectmanager.viewmodel.ProjectViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    val projectViewModel: ProjectViewModel = viewModel()

                    val projects by projectViewModel.projects
                        .collectAsStateWithLifecycle()

                    NavHost(
                        navController = navController,
                        startDestination = "projects"
                    ) {
                        composable("projects") {
                            ProjectsListScreen(
                                projects = projects,
                                onAddClick = {
                                    navController.navigate("add")
                                },
                                onProjectClick = { project ->
                                    navController.navigate(
                                        "details/${project.id}"
                                    )
                                }
                            )
                        }

                        composable("add") {
                            AddProjectScreen(
                                onCreate = { name, description ->
                                    projectViewModel.addProject(
                                        name,
                                        description
                                    )
                                    navController.popBackStack()
                                },
                                onCancel = {
                                    navController.popBackStack()
                                }
                            )
                        }

                        composable(
                            route = "details/{projectId}",
                            arguments = listOf(
                                navArgument("projectId") {
                                    type = NavType.IntType
                                }
                            )
                        ) { entry ->

                            val projectId = entry.arguments
                                ?.getInt("projectId") ?: -1

                            val project = projects.find {
                                it.id == projectId
                            }

                            DetailsProjectScreen(
                                project = project,
                                onProgressChange = { progress ->
                                    projectViewModel.updateProgress(
                                        projectId,
                                        progress
                                    )
                                },
                                onDelete = {
                                    projectViewModel.deleteProject(projectId)
                                    navController.popBackStack()
                                },
                                onBack = {
                                    navController.popBackStack()
                                },
                                onEdit = { name, description ->
                                    projectViewModel.updateProject(
                                        projectId,
                                        name,
                                        description
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
