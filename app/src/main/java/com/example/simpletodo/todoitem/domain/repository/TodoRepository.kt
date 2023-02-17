package com.example.simpletodo.todoitem.domain.repository

import com.example.simpletodo.todoitem.domain.model.*
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodoItems(): Flow<List<TodoItem>>
    fun getTodoItemsWithTags(): Flow<List<TodoItemWithTags>>
    suspend fun getTodoItemById(id: Int): TodoItem?
    suspend fun getTodoItemWithTagsById(id: Int): TodoItemWithTags?
    suspend fun insertTodoItem(todoItem: TodoItem): Long
    suspend fun deleteTodoItem(todoItem: TodoItem)
    suspend fun updateTodoItem(todoItem: TodoItem)

    fun getTags(): Flow<List<Tag>>
    fun getTagsWithTodoItems(): Flow<List<TagWithTodoItems>>
    suspend fun getTagById(id: Int): Tag?
    suspend fun getTagWithTodoItemsById(id: Int): TagWithTodoItems?
    suspend fun addTagToTodoItem(tag: Tag, todoItemId: Int)
    suspend fun deleteTodoItemTag(tagId: Int, todoItemId: Int)
}