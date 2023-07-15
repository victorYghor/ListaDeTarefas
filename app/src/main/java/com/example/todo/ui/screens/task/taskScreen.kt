package com.example.todo.ui.screens.task

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
    BackHandler(onBackPressed = { navigateToListScreen(Action.NO_ACTION) })
    val title: String = sharedViewModel.title
    val description: String = sharedViewModel.description
    val priority:Priority = sharedViewModel.priority

    val context = LocalContext.current

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
            TaskContent(
                title = title,
                onTitleChange = {
                    sharedViewModel.updateTitle(it)
                },
                description = description,
                onDescriptionChange = {
                    sharedViewModel.updateDescription(it)
                },
                priority = priority,
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

@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}