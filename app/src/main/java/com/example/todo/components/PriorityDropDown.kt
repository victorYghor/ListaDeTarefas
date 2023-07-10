package com.example.todo.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todo.R
import com.example.todo.data.models.Priority
import com.example.todo.ui.theme.P_PRIORITY
import com.example.todo.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.todo.ui.theme.textColor
import com.example.todo.ui.theme.topAppBarColor

@Composable
fun PriorityDropDown(
    priority: Priority,
    onPrioritySelected: (Priority) -> Unit,
    modifier: Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val angle by animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(TOP_APP_BAR_HEIGHT)
            .clickable(onClick = { expanded = true })
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0f),
                shape = MaterialTheme.shapes.small
            )
            .background(MaterialTheme.colorScheme.background),
        verticalAlignment = Alignment.CenterVertically

    ) {
        Canvas(
            modifier = Modifier
                .size(P_PRIORITY)
                .weight(1f)
        ) {
            drawCircle(color = priority.color)
        }

        Text(text = priority.name, color = textColor, modifier = Modifier.weight(8f))

        IconButton(
            onClick = { expanded = true },
            modifier = Modifier
                .weight(1.5f)
                .alpha(5f)
                .rotate(angle)
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_menu_icon)
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(fraction = 0.94f)
        ) {
            Priority.values().forEach {
                if (it != Priority.MEDIUM){
                    DropdownMenuItem(
                        text = { PriorityItem(priority = it) },
                        onClick = {
                            expanded = false
                            onPrioritySelected(it)
                        }
                    )
                }
            }
        }
    }

}

@Composable
@Preview
fun PriorityDropDownMenu() {
    PriorityDropDown(priority = Priority.HIGH, onPrioritySelected = {}, modifier = Modifier)
}