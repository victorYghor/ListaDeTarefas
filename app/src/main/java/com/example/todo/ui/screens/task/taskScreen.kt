package com.example.todo.ui.screens.task

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todo.data.models.ToDoTask
import com.example.todo.util.Action

// it will be implement something in the future
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?
) {
    Scaffold(
        topBar = {
                 TaskAppBar(
                     navigateToListScreen = navigateToListScreen,
                 selectedTask = selectedTask
                 )
        },
        content = { padding ->
            Text("test", modifier = Modifier.padding(padding))
        }
        )
}