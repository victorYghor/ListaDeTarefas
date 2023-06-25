package com.example.todo.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.ToDoTask
import com.example.todo.data.repositories.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository
    ): ViewModel() {
    private val _allTasks =
        MutableStateFlow<List<ToDoTask>>(emptyList())

    fun getAllTasks() {
        viewModelScope.launch {
            repository.getAllTasks.collect {
                _allTasks.value = it
            }
        }
    }
}