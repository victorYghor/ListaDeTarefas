package com.example.todo.ui.screens.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.ui.theme.HighPriorityColor
import com.example.todo.ui.theme.LARGEST_PADDING
import com.example.todo.ui.theme.P_LARGE
import com.example.todo.ui.theme.P_PRIORITY
import com.example.todo.ui.theme.P_SMALL
import com.example.todo.ui.theme.TASK_ITEM_ELEVATION
import com.example.todo.ui.theme.taskItemBackgroundColor
import com.example.todo.ui.theme.textColor
import com.example.todo.util.Action
import com.example.todo.util.RequestState
import com.example.todo.util.SearchAppBarState

@Composable
fun ListContent(
    allTasks: RequestState<List<ToDoTask>>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    modifier: Modifier,
    searchedTasks: RequestState<List<ToDoTask>>,
    searchAppBarState: SearchAppBarState,
    lowPriorityTasks: List<ToDoTask>,
    highPriorityTasks: List<ToDoTask>,
    sortState: RequestState<Priority>,
    onSwipeToDelete: (Action, ToDoTask) -> Unit
) {
    if (sortState is RequestState.Success) {
        when {
            searchAppBarState == SearchAppBarState.TRIGGERED -> {
                if (searchedTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = searchedTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete
                    )
                }
            }
            sortState.data == Priority.NONE -> {
                if (allTasks is RequestState.Success) {
                    HandleListContent(
                        tasks = allTasks.data,
                        navigateToTaskScreen = navigateToTaskScreen,
                        onSwipeToDelete
                    )
                }
            }
            sortState.data == Priority.LOW -> {
                HandleListContent(
                    tasks = lowPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete
                )
            }
            sortState.data == Priority.HIGH -> {
                HandleListContent(
                    tasks = highPriorityTasks,
                    navigateToTaskScreen = navigateToTaskScreen,
                    onSwipeToDelete
                )
            }
        }

    }


}

@Composable
fun HandleListContent(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit
) {
    if (tasks.isEmpty()) {
        EmptyContent()
    } else {
        DisplayTasks(
            tasks = tasks,
            navigateToTaskScreen = navigateToTaskScreen,
            onSwipeToDelete = onSwipeToDelete
        )
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(HighPriorityColor)
            .padding(horizontal = LARGEST_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(R.string.delete_icon),
            tint = Color.White,
            modifier = Modifier.rotate(degrees = degrees)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskItem(
    toDoTask: ToDoTask,
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = taskItemBackgroundColor,
        shape = RectangleShape,
        tonalElevation = TASK_ITEM_ELEVATION,
        shadowElevation = TASK_ITEM_ELEVATION,
        onClick = {
            navigateToTaskScreen(toDoTask.id)
        }
        ) {
        Column(
            modifier= Modifier
                .padding(all = P_LARGE)
                .fillMaxWidth()) {
            Row {
                Text(
                    text = toDoTask.title,
                    color = textColor,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    modifier = Modifier.weight(8f)
                )
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                    contentAlignment = Alignment.Center
                    ) {
                    Canvas(modifier = Modifier
                        .size(P_PRIORITY)) {
                        drawCircle(
                            color = toDoTask.priority.color
                        )
                    }
                }
            }
            Text(
                text = toDoTask.description,
                color = textColor,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DisplayTasks(
    tasks: List<ToDoTask>,
    navigateToTaskScreen: (taskId: Int) -> Unit,
    onSwipeToDelete: (Action, ToDoTask) -> Unit
) {
    LazyColumn(modifier = Modifier) {
        items(items = tasks,key = {
                task -> task.id
        }) { task ->
            val dismissState = rememberDismissState()
            val dismissDirection = dismissState.dismissDirection
            val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

            if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
                onSwipeToDelete(Action.DELETE, task)
            }

            val degrees by animateFloatAsState(
                if (dismissState.targetValue == DismissValue.Default)
                    0f
                else
                    -45f
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = { RedBackground(degrees = degrees)},
                dismissThresholds = { FractionalThreshold(0.2f) },
                dismissContent = {
                    TaskItem(
                        toDoTask = task,
                        navigateToTaskScreen = navigateToTaskScreen
                    )
                }
            )

        }
    }
}

@Composable
@Preview
fun TaskItemPreview() {
    TaskItem(
        toDoTask = ToDoTask(
            id = 0,
            title = "Title",
            description = "A good description for a task",
            priority = Priority.MEDIUM
        ),
        navigateToTaskScreen = {}
    )
}