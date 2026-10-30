
package com.example.projectmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import com.example.projectmanager.data.Project

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsProjectScreen(
    project: Project?,
    onProgressChange: (Float) -> Unit,
    onDelete: () -> Unit,
    onBack: () -> Unit,
    onEdit: (String, String) -> Unit
) {
    if (project == null) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Деталі проєкту") },
                    navigationIcon = {
                        TextButton(onClick = onBack) {
                            Text("Назад")
                        }
                    }
                )
            }
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Text("Проєкт не знайдено")
            }
        }
        return
    }

    var showEditDialog by remember(project.id) {
        mutableStateOf(false)
    }

    var showDeleteDialog by remember(project.id) {
        mutableStateOf(false)
    }

    var editName by remember(project.id) {
        mutableStateOf(project.name)
    }

    var editDescription by remember(project.id) {
        mutableStateOf(project.description)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Деталі проєкту") },
                navigationIcon = {
                    TextButton(onClick = onBack) {
                        Text("Назад")
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = project.name,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = project.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Прогрес",
                        style = MaterialTheme.typography.titleMedium
                    )

                    Text(
                        text = "${project.progress.toInt()}%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Slider(
                        value = project.progress.coerceIn(0f, 100f),
                        onValueChange = onProgressChange,
                        valueRange = 0f..100f
                    )
                }
            }

            Button(
                onClick = {
                    editName = project.name
                    editDescription = project.description
                    showEditDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Редагувати")
            }

            OutlinedButton(
                onClick = {
                    showDeleteDialog = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Видалити")
            }
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
            },
            title = {
                Text("Редагувати проєкт")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Назва проєкту") },
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = editDescription,
                        onValueChange = { editDescription = it },
                        label = { Text("Опис") }
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (editName.isNotBlank()) {
                            onEdit(
                                editName,
                                editDescription
                            )
                            showEditDialog = false
                        }
                    }
                ) {
                    Text("Зберегти")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEditDialog = false
                    }
                ) {
                    Text("Скасувати")
                }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text("Видалити проєкт?")
            },
            text = {
                Text("Ви впевнені, що хочете видалити цей проєкт?")
            },
            confirmButton = {
                TextButton(
                    onClick = onDelete
                ) {
                    Text("Видалити")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text("Скасувати")
                }
            }
        )
    }
}
