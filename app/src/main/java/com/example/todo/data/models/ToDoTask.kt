package com.example.todo.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todo.util.Constants.DATABASE_TABLE

// Where I am explain how the database needs initialize a table
@Entity(tableName = DATABASE_TABLE)
data class ToDoTask (
    val title: String,
    val description: String,
    val priority: Priority,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)