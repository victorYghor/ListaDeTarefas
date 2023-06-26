package com.example.todo.ui.screens.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.theme.*
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.SearchAppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navigateToTaskScreen: (Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    val searchAppBarState: SearchAppBarState by sharedViewModel.searchAppBarState
    val searchTextState: String by sharedViewModel.searchTextState
    Scaffold(
        content = {
            Column(modifier = Modifier.padding(it)) {
                Text("test")
            }
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
        }
    )
}

@Composable
fun ListFab(
    navigateToTaskScreen: (taskId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            navigateToTaskScreen(-1)
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