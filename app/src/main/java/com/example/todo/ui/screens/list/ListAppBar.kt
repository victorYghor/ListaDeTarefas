package com.example.todo.ui.screens.list

import android.app.appsearch.AppSearchManager.SearchContext
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.components.PriorityItem
import com.example.todo.data.models.Priority
import com.example.todo.ui.theme.P_LARGE
import com.example.todo.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.todo.ui.theme.textColor
import com.example.todo.ui.theme.topAppBarColor
import com.example.todo.ui.viewModels.SharedViewModel
import com.example.todo.util.SearchAppBarState
import com.example.todo.util.TrailingIconState

@Composable
fun ListAppBar(
    sharedViewModel: SharedViewModel,
    searchAppBarState: SearchAppBarState,
    searchTextState: String
) {
    when(searchAppBarState) {
        SearchAppBarState.CLOSED -> {
            DefaultListAppBar(
                onSearchClicked = {
                                  sharedViewModel.searchAppBarState.value = SearchAppBarState.OPENED
                },
                onSortClicked = {},
                onDeleteClicked = {},
            )
        }
        else -> {
            SearchAppBar(
                text = searchTextState,
                onTextChange = { newText -> sharedViewModel.searchTextState.value = newText },
                onCloseClicked = {
                                 sharedViewModel.searchAppBarState.value =
                                     SearchAppBarState.CLOSED
                    sharedViewModel.searchTextState.value = ""
                },
                onSearchClicked = {}
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultListAppBar(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.tasks),
                color = textColor
            )
        },
        actions = {
                  ListAppBarActions(
                      onSearchClicked = onSearchClicked,
                      onSortClicked = onSortClicked,
                      onDeleteClicked = onDeleteClicked
                  )
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = topAppBarColor),

        )
}


@Composable
fun ListAppBarActions(
    onSearchClicked: () -> Unit,
    onSortClicked: (Priority) -> Unit,
    onDeleteClicked: () -> Unit
) {
    SearchAction(onSearchClicked)
    SortAction(onSortClicked = onSortClicked)
    DeleteAllAction(onDeleteClicked)
}

@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = { onSearchClicked() },
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(R.string.search_icon),
            tint = textColor
        )
    }
}

@Composable
fun SortAction(
    onSortClicked: (Priority) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    IconButton(
        onClick = { expanded = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_filter_list_24),
            contentDescription = stringResource(R.string.sort_task),
            tint = textColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            Priority.values().forEach {
                DropdownMenuItem(text = { PriorityItem(priority = it) },
                    onClick = {
                        expanded = false
                        onSortClicked(it)
                    })
            }
        }
    }
}

@Composable
fun DeleteAllAction(
    onDeleteClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    IconButton(
        onClick = { expanded = true}
    ) {
        Icon(
            painter = painterResource(id = R.drawable.baseline_more_vert_24),
            contentDescription = stringResource(R.string.delete_all_tasks),
            tint = textColor
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(text = {
                Text(text = stringResource(R.string.delete_all_tasks),
                    style = MaterialTheme.typography.bodyMedium,
                    color = textColor,
                    modifier = Modifier.padding(start = P_LARGE)
                    ) },
                onClick = {
                    expanded = false
                    onDeleteClicked()
                },
                )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {

    var trailingIconState by remember {
        mutableStateOf(TrailingIconState.READY_TO_DELETE)
    }
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT),
        color = topAppBarColor
    ) {
        TextField(
            value = text,

            onValueChange = {
            onTextChange(it)
        },

        modifier = Modifier.fillMaxWidth(),

        placeholder = {
            Text(
                text = stringResource(R.string.search),
                color = textColor,
                modifier = Modifier.alpha(0.5f)
            )
        },
            textStyle = TextStyle(
                color = textColor,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,

            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    onClick = {},
                    modifier = Modifier.alpha(0.7f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = stringResource(R.string.search_icon),
                        tint = textColor
                        )
                }
            },
            trailingIcon = {
                IconButton(onClick = {
                    when(trailingIconState) {
                        TrailingIconState.READY_TO_DELETE -> {
                            onTextChange("")
                            trailingIconState = TrailingIconState.READY_TO_CLOSE
                        }
                        TrailingIconState.READY_TO_CLOSE -> {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                                trailingIconState = TrailingIconState.READY_TO_DELETE
                            }
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = stringResource(R.string.close_icon),
                        tint = textColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = topAppBarColor

            )
        )
    }
}


@Composable
@Preview
fun defaultListAppBarPreview() {
    DefaultListAppBar(
        onSearchClicked = {},
        onSortClicked = {},
        onDeleteClicked = {}
    )
}
@Composable
@Preview
private fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Search",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}