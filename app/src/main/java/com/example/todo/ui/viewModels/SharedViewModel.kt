package com.example.todo.ui.viewModels

import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.models.Priority
import com.example.todo.data.models.ToDoTask
import com.example.todo.data.repositories.DataStoreRepository
import com.example.todo.data.repositories.ToDoRepository
import com.example.todo.ui.screens.list.SearchAppBar
import com.example.todo.util.Action
import com.example.todo.util.Constants.MAX_TITLE_LENGTH
import com.example.todo.util.RequestState
import com.example.todo.util.SearchAppBarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: ToDoRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    var action: MutableStateFlow<Action> = MutableStateFlow(Action.NO_ACTION)
    // here I make a Task to organize my code

    var task = MutableStateFlow(ToDoTask(
        id = 0,
        title = "",
        description = "",
        priority = Priority.NONE
    ))
        private set


    val searchAppBarState: MutableState<SearchAppBarState> =
        mutableStateOf(SearchAppBarState.CLOSED)

    val searchTextState: MutableState<String> = mutableStateOf("")

    private val _allTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)

    val allTasks: StateFlow<RequestState<List<ToDoTask>>> = _allTasks
    // for some reason, this line of code is broken in my project
//    val allTasks: StateFlow<List<ToDoTask>> = _allTasks

    private val _searchedTasks =
        MutableStateFlow<RequestState<List<ToDoTask>>>(RequestState.Idle)
    val searchedTasks: StateFlow<RequestState<List<ToDoTask>>> = _searchedTasks

    fun persistSortingState(priority: Priority) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.persistSortState(priority = priority)
        }
    }

    private val _sortState = MutableStateFlow<RequestState<Priority>>(RequestState.Idle)

    val sortState: StateFlow<RequestState<Priority>> = _sortState

    fun readSortState() {
        _sortState.value = RequestState.Loading

        try {
            viewModelScope.launch {
                dataStoreRepository.readSortState
                    .map { Priority.valueOf(it) }.collect {
                        _sortState.value = RequestState.Success(it)
                    }
            }
        } catch (e: Exception) {
            _sortState.value = RequestState.Error(e)
        }
    }

    fun searchDatabase(searchQuery: String) {
        _searchedTasks.value = RequestState.Loading

        try {
            viewModelScope.launch {
                repository.searchDatabase(searchQuery = "%$searchQuery%")
                    .collect { searchedTasks ->
                        _searchedTasks.value = RequestState.Success(searchedTasks)
                    }
            }
        } catch (e: Exception) {
            _searchedTasks.value = RequestState.Error(e)
        }
        searchAppBarState.value = SearchAppBarState.TRIGGERED
    }

    fun getAllTasks() {
        _allTasks.value = RequestState.Loading

        try {
            viewModelScope.launch {
                repository.getAllTasks.collect {
                    _allTasks.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allTasks.value = RequestState.Error(e)
        }

    }

    private val _selectedTask: MutableStateFlow<ToDoTask?> = MutableStateFlow(null)

    val selectedTask: StateFlow<ToDoTask?> = _selectedTask
    fun getSelectedTask(taskId: Int) {
        viewModelScope.launch {
            repository.getSelectedTask(taskId = taskId).collect { task ->
                _selectedTask.value = task
            }
        }
    }

    fun addTask() {
        viewModelScope.launch(Dispatchers.IO) {
            val toDoTask = ToDoTask(
                title = task.value.title,
                description = task.value.description,
                priority = task.value.priority
            )
            repository.addTask(toDoTask = toDoTask)
        }
        searchAppBarState.value
    }

    private fun updateTask() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTask(toDoTask = task.value)
        }
    }

    private fun deleteTask() {
        viewModelScope.launch {
            // Here I make a task to delete them in the repository, there is some way that I could be use the task that I already made
            repository.deleteTask(toDoTask = task.value)
        }
    }

    fun lowPriorityTasks(): StateFlow<List<ToDoTask>> =
        repository.sortByLowPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    fun highPriorityTasks(): StateFlow<List<ToDoTask>> =
        repository.sortByHighPriority.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            emptyList()
        )

    private fun deleteAllTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllTasks()
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD -> {
                addTask()
            }

            Action.UPDATE -> {
                updateTask()
            }

            Action.DELETE -> {
                deleteTask()
            }

            Action.DELETE_ALL -> {
                deleteAllTasks()
            }

            Action.UNDO -> {
                addTask()
            }

            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateTaskFields(selectedTask: ToDoTask) {
        updateTitle(selectedTask.title)
        updatePriority(selectedTask.priority)
        updateDescription(selectedTask.description)
    }

    fun updateDescription(newDescription: String) {
        if (newDescription.length < MAX_TITLE_LENGTH) {
            task.update { it -> it.copy(title = newDescription) }
        }
    }

    fun updateTitle(newTitle: String) {
        task.update { it -> it.copy(title = newTitle) }
    }

    fun updatePriority(newPriority: Priority) {
        task.update { it -> it.copy(priority = newPriority) }
    }


    fun validateFields(): Boolean {
        return task.value.title.isNotEmpty() && task.value.description.isNotEmpty()
    }
}