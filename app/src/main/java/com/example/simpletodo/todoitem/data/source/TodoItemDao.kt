package com.example.simpletodo.todoitem.data.source

import androidx.room.*
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.model.TodoItemWithTags
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todo_item")
    fun getTodoItems(): Flow<List<TodoItem>>

    @Transaction
    @Query("SELECT * FROM todo_item")
    fun getTodoItemsWithTags(): Flow<List<TodoItemWithTags>>

    @Query("SELECT * FROM todo_item WHERE todoItemId = :id")
    suspend fun getTodoItemById(id: Int): TodoItem?

    @Query("SELECT * FROM todo_item WHERE todoItemId = :id")
    suspend fun getTodoItemWithTagsById(id: Int): TodoItemWithTags?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodoItem(todoItem: TodoItem) : Long

    @Delete
    suspend fun deleteTodoItem(todoItem: TodoItem)

    @Update
    suspend fun updateTodoItem(todoItem: TodoItem)

    @Query("SELECT EXISTS (SELECT * FROM todo_item WHERE todoItemId= :todoItemId)")
    suspend fun getTodoItemExistsById(todoItemId: Int) : Boolean
}