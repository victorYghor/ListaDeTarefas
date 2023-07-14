package com.example.todo.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.todo.navigation.destinations.listComposable
import com.example.todo.navigation.destinations.taskComposable
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.Constants.LIST_SCREEN
import com.example.todo.util.Constants.SPLASH_SCREEN

@Composable
fun SetupNavigation(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    // elemento compose
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {

        listComposable(
            navigateToTaskScreen = screen.task,
            sharedViewModel = sharedViewModel
        )
        taskComposable (
            navigateToListScreen = screen.list,
            sharedViewModel = sharedViewModel
        )
    }
}