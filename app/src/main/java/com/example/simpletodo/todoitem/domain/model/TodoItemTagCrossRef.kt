package com.example.simpletodo.todoitem.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Junction

@Entity(tableName = "todo_item_tag", primaryKeys = ["todoItemId", "tagId"] /*["todoItemId", "tagId"]*/)
data class TodoItemTagCrossRef(
    //@ColumnInfo(name = "todoItemId")
    val todoItemId: Int,
    //@ColumnInfo(name = "tagId")
    val tagId: Int
)
