package com.example.simpletodo.todoitem.data.source

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.model.TodoItemTagCrossRef

@Database(
    entities = [TodoItem::class, Tag::class, TodoItemTagCrossRef::class],
    version = 1,
    exportSchema = true
)
abstract class TodoDatabase: RoomDatabase() {
    abstract val todoItemDao: TodoItemDao
    abstract val tagDao: TagDao

    companion object {
         const val DATABASE_NAME = "todo_db"
    }
}