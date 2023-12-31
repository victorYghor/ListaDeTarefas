package com.example.todo.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.theme.DarkGray
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.Action

// it will be implement something in the future
// this is the screen of task
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskScreen(
    navigateToListScreen: (Action) -> Unit,
    selectedTask: ToDoTask?,
    sharedViewModel: SharedViewModel
) {

    val task by sharedViewModel.task.collectAsState()

    val context = LocalContext.current

    BackHandler(onBack = { navigateToListScreen(Action.NO_ACTION) })

    Scaffold(
        topBar = {
            TaskAppBar(
                selectedTask = selectedTask,
                // where come this action parameter
                navigateToListScreen = {action ->
                    if (action == Action.NO_ACTION) {
                        navigateToListScreen(action)
                    } else {
                        if (sharedViewModel.validateFields()) {
                            navigateToListScreen(action)
                        } else {
                            displayToast(context = context)
                        }
                    }
                },
                modifier = Modifier
            )
        },
        content = { paddingValues ->
            // null InputStream
            TaskContent(
                title = task.title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                description = task.description,
                onDescriptionChange = {
                    sharedViewModel.updateDescription(it)
                },
                priority = task.priority,
                onPrioritySelected = {
                    sharedViewModel.updatePriority(it)
                },
                modifier = Modifier
                    .padding(top = paddingValues.calculateTopPadding())
                    .background(DarkGray),
            )
        },
    )
}
fun displayToast(context: Context) {
    Toast.makeText(context, context.getString(R.string.fields_empty), Toast.LENGTH_SHORT).show()
}

@Preview
@Composable
fun PreviewScreen() {

}
