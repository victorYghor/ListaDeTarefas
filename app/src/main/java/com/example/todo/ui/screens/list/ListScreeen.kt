package com.example.todo.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.todo.R
import com.example.todo.ui.theme.*
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.Action
import com.example.todo.util.SearchAppBarState
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel,
    action: Action,
) {


    LaunchedEffect(key1 = action) {
        sharedViewModel.handleDatabaseActions(action)
    }

    val action = sharedViewModel.action
    val sortState by sharedViewModel.sortState.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchedTasks by sharedViewModel.searchedTasks.collectAsState()

    val lowPriorityTasks by sharedViewModel.lowPriorityTasks().collectAsState()
    val highPriorityTasks by sharedViewModel.highPriorityTasks().collectAsState()

    val scaffoldState = rememberScaffoldState()

    
    DisplaySnackBar(
        scaffoldState = scaffoldState,
        handleDatabaseActions = { sharedViewModel.handleDatabaseActions(action = action) },
        taskTitle = sharedViewModel.title,
        action = action,
        onUndoClicked = {
            sharedViewModel.updateAction(it)
        },
        onComplete = { sharedViewModel.updateAction(it)}
    )

    Scaffold(
        topBar = {
                 ListAppBar(
                     sharedViewModel = sharedViewModel,
                     searchAppBarState = searchAppBarState,
                     searchTextState = searchTextState
                 )
        },
        content = { paddingValues ->
            ListContent(
                allTasks = allTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                modifier = Modifier.padding(paddingValues),
                searchedTasks = searchedTasks,
                searchAppBarState = searchAppBarState,
                lowPriorityTasks = lowPriorityTasks,
                highPriorityTasks = highPriorityTasks,
                sortState = sortState,
                onSwipeToDelete = { action, task ->
                    sharedViewModel.updateAction(action)
                    sharedViewModel.updateTaskFields(selectedTask = task)
                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                }
            )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen)
        },
        scaffoldState = scaffoldState
    )
}

@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action,
    onUndoClicked: (Action) -> Unit,
    onComplete: (Action) -> Unit
) {

    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
                    message = setMessage(action, taskTitle), actionLabel = setActionLabel(action)
                )
                undoDeletedTask(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
        onComplete(Action.NO_ACTION)
    }
}

@Composable
fun ListFab(
    onFabClicked: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1)
        },
        containerColor = fabBackgroundColor
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_button),
            tint = textColor
        )
    }
}

private fun setMessage(action: Action, taskTitle: String): String {
    return when (action) {
        Action.DELETE_ALL -> "All Tasks Removed."
        else -> "${action.name}: $taskTitle"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action.name == "DELETE") "UNDO" else "OK"
}

private fun undoDeletedTask(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (
        snackBarResult == SnackbarResult.ActionPerformed
        && action == Action.DELETE
    ) {
        onUndoClicked(Action.UNDO)
    }
}