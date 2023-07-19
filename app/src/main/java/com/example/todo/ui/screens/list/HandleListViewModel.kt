package com.example.todo.ui.screens.list

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.ToDoTask
import com.example.todo.util.Action
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HandleListViewModel: ViewModel(){
    fun check(
        tasks: List<ToDoTask>,
        navigateToTaskScreen: (taskId: Int) -> Unit,
        onSwipeToDelete: (Action, ToDoTask) -> Unit
    ) {
        var job: Job? = null
        val test = viewModelScope.launch {
            if (tasks.isEmpty()) {
                job = viewModelScope.launch {
                    delay(500)
                    EmptyContent()
                    // usar navigate to task screeen para levar o composable empty content
                }
            } else {
                job?.let { job!!.cancel() }
//                DisplayTasks(
//                    tasks = tasks,
//                    navigateToTaskScreen = navigateToTaskScreen,
//                    onSwipeToDelete = onSwipeToDelete
//                )
//                fazer a navegação para exibir a display task
            }

        }
    }
}
