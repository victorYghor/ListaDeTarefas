package com.example.todo.navigation

import androidx.navigation.NavController
import com.example.todo.util.Action
import com.example.todo.util.Constants.LIST_SCREEN
import com.example.todo.util.Constants.SPLASH_SCREEN

// why when I use NavController.navigate() I have a problem and when I use navController.navigate()
// I don't have a problem? because NavController is a class and need to be instantiate to be called
class Screens(navController: NavController) {
//    val splash: () -> Unit = {
//        navController.navigate(route = "list/${Action.NO_ACTION.name}") {
//            popUpTo(SPLASH_SCREEN) { inclusive = true }
//        }
//    }
    val list: (Action) -> Unit = { action ->
        navController.navigate("list/${action.name}"){
            popUpTo(LIST_SCREEN) { inclusive = true }
        }
    }
    val task: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }
}