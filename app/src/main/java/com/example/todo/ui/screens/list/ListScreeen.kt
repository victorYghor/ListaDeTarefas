package com.example.todo.ui.screens.list

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.todo.R
import com.example.todo.ui.theme.*
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.Action
import com.example.todo.util.SearchAppBarState
import kotlinx.coroutines.launch
import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllTasks()
    }

    val action by sharedViewModel.action

    val allTasks by sharedViewModel.allTasks.collectAsState()
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState

    sharedViewModel.handleDatabaseActions(action = action)



    Scaffold(
        content = { paddingValues ->
            ListContent(
                tasks = allTasks,
                navigateToTaskScreen = navigateToTaskScreen,
                modifier = Modifier.padding(paddingValues)
            )
        },
        topBar = {
                 ListAppBar(
                     sharedViewModel = sharedViewModel,
                     searchAppBarState = searchAppBarState,
                     searchTextState = searchTextState
                 )
        },
        floatingActionButton = {
            ListFab(navigateToTaskScreen)
        },
    )
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
// devs android que quiserem me socorrer, estou aceitando
@Composable
fun DisplaySnackBar(
    scaffoldState: ScaffoldState,
    handleDatabaseActions: () -> Unit,
    taskTitle: String,
    action: Action
) {

    handleDatabaseActions()

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            this.launch {
//                val snackBarResult = scaffoldState.snackBarHostState.showSnackBar(
            //                message = "${action.name}: $taskTitle", actionLabel = "OK"
            //                )
                // is not possible to use this because this parameter of this type do not exist
            }
        }
    }
}
