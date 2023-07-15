package com.example.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.todo.data.models.ToDoTask
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo_table ORDER BY id ASC")
    fun getAllTasks(): Flow<List<ToDoTask>>

    @Query("SELECT * FROM todo_table WHERE id=:taskId")
    fun getSelectedTask(taskId: Int): Flow<ToDoTask>

    // IF A ERROR EXIST MAYBE THIS IS THE CAUSE
    @Upsert
    suspend fun addTask(toDoTask: ToDoTask)

    @Update
    suspend fun updateTask(toDoTask: ToDoTask)

    @Delete
    suspend fun deleteTask(toDoTask: ToDoTask)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTasks()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery OR description LIKE :searchQuery")
    fun searchDatabase(searchQuery: String): Flow<List<ToDoTask>>

    @Query("""
        SELECT * FROM todo_table ORDER BY 
            CASE
                WHEN priority LIKE 'L%' THEN 1 
                WHEN priority LIKE 'M%' THEN 2 
                WHEN priority LIKE 'H%' THEN 3 
            END
        """)
    fun sortByLowPriority(): Flow<List<ToDoTask>>

    @Query("""
        SELECT * FROM todo_table ORDER BY 
            CASE
                WHEN priority LIKE 'H%' THEN 1 
                WHEN priority LIKE 'M%' THEN 2 
                WHEN priority LIKE 'L%' THEN 3 
            END
        """)
    fun sortByHighPriority(): Flow<List<ToDoTask>>
}