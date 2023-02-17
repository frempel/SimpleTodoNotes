package com.example.simpletodo.todoitem.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_item")
data class TodoItem(
    @PrimaryKey
    @ColumnInfo(name = "todoItemId")
    val id: Int? = null,
    val title: String,
    val description: String,
    val timestamp: Long,
) {}

class TodoItemBlankTitleException(): Exception()