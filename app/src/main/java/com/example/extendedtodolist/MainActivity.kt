package com.example.extendedtodolist

import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                TodoApp()
            }
        }
    }
}

@Composable
fun TodoApp() {
    var tasks by remember { mutableStateOf(listOf<Task>()) }
    var taskText by remember { mutableStateOf("") }
    var editingTask by remember { mutableStateOf<Task?>(null) }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri.toString()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = taskText,
            onValueChange = { taskText = it },
            label = { Text("Add new task") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { imagePickerLauncher.launch("image/*") } ) {
            Text("Attach Image")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (taskText.isNotBlank()) {
                    val currentTask = Task(text = taskText, imageUri = selectedImageUri)
                    tasks = tasks + currentTask
                    taskText = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Task")
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tasks) { task ->
                TaskItem(
                    task = task,
                    onEdit = { selectedTask -> editingTask = selectedTask },
                    onDelete = { tasks = tasks.filterNot { t -> t.id == it.id } }
                )
            }
        }
    }

    editingTask?.let { task ->
        EditTaskDialog(
            task = task,
            onDismiss = { editingTask = null },
            onUpdate = { updatedText ->
                if (updatedText.isNotBlank()) {
                    tasks = tasks.map {
                        if (it.id == task.id) {
                            it.copy(text = updatedText, imageUri = selectedImageUri)
                        } else it
                    }
                    editingTask = null
                }
            },
            onPickImage = { imagePickerLauncher.launch("image/*") }
        )
    }
}
@Composable
fun TaskItem(task: Task, onEdit: (Task) -> Unit, onDelete: (Task) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = task.text, style = MaterialTheme.typography.bodyLarge)
                task.imageUri?.let { uri ->
                    AsyncImage(
                        model = uri,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                    )
                }
            }
            IconButton(onClick = { onEdit(task) }) {
                Text("Edit")
            }
            IconButton(onClick = { onDelete(task) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun EditTaskDialog(task: Task, onDismiss: () -> Unit, onUpdate: (String) -> Unit, onPickImage: () -> Unit) {
    var editedText by remember { mutableStateOf(task.text) }
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Task") },
        text = {
            Column {
                OutlinedTextField(
                    value = editedText,
                    onValueChange = { editedText = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onPickImage ) {
                    Text("Attach Image")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onUpdate(editedText) }) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

data class Task(
    val id: Long = System.currentTimeMillis(),
    val text: String,
    val imageUri: String? = null
)
