package com.example.todo.ui.screens.task

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.R
import com.example.todo.ui.theme.textColor
import com.example.todo.ui.theme.topAppBarColor
import com.example.todo.util.Action

@Composable
fun TaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    NewTaskAppBar(navigateToListScreen = navigateToListScreen)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskAppBar(
    navigateToListScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
                         BackAction(onBackClicked = navigateToListScreen)
        },
        title = {
            Text(
                text = stringResource(R.string.add_task),
                color = textColor
            )
        },
        modifier = Modifier.background(topAppBarColor),
        actions = {
                  AddAction(onAddClicked = navigateToListScreen)
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = topAppBarColor)
    )
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onBackClicked(Action.NO_ACTION)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_arrow),
            tint = textColor
        )

    }
}

@Composable
fun AddAction(
    onAddClicked: (Action) -> Unit
) {
    IconButton(
        onClick = {
            onAddClicked(Action.ADD)
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(R.string.check_icon),
            tint = textColor
        )

    }
}

@Composable
@Preview
fun NewTaskAppBarPreview() {
    TaskAppBar(navigateToListScreen = {})
}