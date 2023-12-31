package com.example.todo.data.repositories

import com.example.todo.data.ToDoDao
import com.example.todo.data.models.ToDoTask
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

// TODO why I need a todoRepository?
@ViewModelScoped
class ToDoRepository @Inject constructor(private val toDoDao: ToDoDao) {
    // TODO is a good practice make in this way  is not better make a data class that unify this information?
    val getAllTasks: Flow<List<ToDoTask>> = toDoDao.getAllTasks()
    val sortByLowPriority = toDoDao.sortByLowPriority()
    val sortByHighPriority = toDoDao.sortByHighPriority()

    fun getSelectedTask(taskId: Int): Flow<ToDoTask> {
        return toDoDao.getSelectedTask(taskId)
    }

    fun getAllTasks() {
        toDoDao.getAllTasks()
    }

    suspend fun addTask(toDoTask: ToDoTask)  {
        toDoDao.addTask(toDoTask)
    }

    suspend fun updateTask(toDoTask: ToDoTask) {
        toDoDao.updateTask(toDoTask)
    }

    suspend fun deleteTask(toDoTask: ToDoTask) {
        toDoDao.deleteTask(toDoTask)
    }

    suspend fun deleteAllTasks() {
        toDoDao.deleteAllTasks()
    }

    suspend fun searchDatabase(searchQuery: String) = toDoDao.searchDatabase(searchQuery)
}