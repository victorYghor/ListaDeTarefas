package com.example.todo.navigation.destinations

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo.ui.screens.task.TaskScreen
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.Action
import com.example.todo.util.Constants.LIST_ARGUMENT_KEY
import com.example.todo.util.Constants.LIST_SCREEN
import com.example.todo.util.Constants.TASK_ARGUMENT_KEY
import com.example.todo.util.Constants.TASK_SCREEN

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = TASK_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) { navBackStackEntry ->
        val taskId = navBackStackEntry.arguments!!.getInt(TASK_ARGUMENT_KEY)

        val selectedTask by sharedViewModel.selectedTask.collectAsState()
        sharedViewModel.getSelectedTask(taskId = taskId)
        Log.d("TaskComposable", taskId.toString())
        TaskScreen(navigateToListScreen = navigateToListScreen,
        selectedTask = selectedTask)
    }
}