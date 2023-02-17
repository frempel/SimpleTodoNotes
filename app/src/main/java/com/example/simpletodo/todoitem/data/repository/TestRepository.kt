package com.example.simpletodo.todoitem.data.repository

import com.example.simpletodo.todoitem.domain.model.*
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf

class TestRepository : TodoRepository {
    private val todoItems = mutableListOf<TodoItem>()
    private val todoItemsFlow = flowOf(todoItems)

    private val tags = mutableListOf<Tag>()
    private val tagsFlow = flowOf(tags)

    private val todoItemsTags = mutableListOf<TodoItemTagCrossRef>()
    private val todoItemsTagsFlow = flowOf(todoItemsTags)

    override fun getTodoItems(): Flow<List<TodoItem>> {
        return todoItemsFlow
    }

    override fun getTodoItemsWithTags(): Flow<List<TodoItemWithTags>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTodoItemById(id: Int): TodoItem? {
        TODO("Not yet implemented")
    }

    override suspend fun getTodoItemWithTagsById(id: Int): TodoItemWithTags? {
        TODO("Not yet implemented")
    }

    override suspend fun insertTodoItem(todoItem: TodoItem): Long {
        todoItems.add(todoItem)
        return todoItems.size.toLong()
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }

    override suspend fun updateTodoItem(todoItem: TodoItem) {
        TODO("Not yet implemented")
    }

    override fun getTags(): Flow<List<Tag>> {
        return tagsFlow
    }

    override fun getTagsWithTodoItems(): Flow<List<TagWithTodoItems>> {
        TODO("Not yet implemented")
    }

    override suspend fun getTagById(id: Int): Tag? {
        TODO("Not yet implemented")
    }

    override suspend fun getTagWithTodoItemsById(id: Int): TagWithTodoItems? {
        TODO("Not yet implemented")
    }

    override suspend fun addTagToTodoItem(tag: Tag, todoItemId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTodoItemTag(tagId: Int, todoItemId: Int) {
        TODO("Not yet implemented")
    }
}