package com.example.todo.ui.screens.task

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.ui.components.PriorityDropDown
import com.example.todo.data.models.Priority
import com.example.todo.ui.theme.P_LARGE
import com.example.todo.ui.theme.P_MEDIUM
import com.example.todo.ui.theme.topAppBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskContent(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    modifier: Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(P_LARGE),

    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { onTitleChange(it) },
            label = { Text(text = stringResource(R.string.title)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(P_MEDIUM).background(MaterialTheme.colorScheme.background))
        PriorityDropDown(
            priority = priority,
            onPrioritySelected = onPrioritySelected,
            modifier = Modifier.border(width = 1.dp, brush = Brush.horizontalGradient(listOf(Color.DarkGray, Color.DarkGray)), shape = RectangleShape)
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(P_MEDIUM)
                .background(MaterialTheme.colorScheme.background)
        )
        OutlinedTextField(
            value = description,

            onValueChange = { onDescriptionChange(it) },
            label = { Text(text = stringResource(R.string.description)) },
            textStyle = MaterialTheme.typography.bodyLarge,
            singleLine = true,
            modifier = Modifier
                .fillMaxSize()
//                .border(width = 1.dp, brush = Brush.horizontalGradient(listOf(Color.DarkGray, Color.DarkGray)), shape = RectangleShape),
        )
    }
}

@Composable
@Preview
private fun TaskContentPreview() {
    TaskContent(
        title = "",
        onTitleChange = {},
        description = "",
        onDescriptionChange = {},
        priority = Priority.HIGH,
        onPrioritySelected = {},
        modifier = Modifier,
    )
}

@Composable
fun HandleBackButton(
    // para que server essa classe ?
    backDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        // why I don't need give a name to the object?
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