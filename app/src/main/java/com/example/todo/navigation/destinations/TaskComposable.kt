package com.example.todo.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todo.util.Action
import com.example.todo.util.Constants.LIST_ARGUMENT_KEY
import com.example.todo.util.Constants.LIST_SCREEN
import com.example.todo.util.Constants.TASK_ARGUMENT_KEY

fun NavGraphBuilder.taskComposable(
    navigateToListScreen: (Action) -> Unit
) {
    composable(
        route = LIST_SCREEN,
        arguments = listOf(navArgument(TASK_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {

    }
}