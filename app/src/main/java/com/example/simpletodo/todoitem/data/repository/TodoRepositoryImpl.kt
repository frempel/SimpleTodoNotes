package com.example.simpletodo.todoitem.data.repository

import com.example.simpletodo.todoitem.data.source.TagDao
import com.example.simpletodo.todoitem.data.source.TodoItemDao
import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.model.TagWithTodoItems
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.model.TodoItemWithTags
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(
    private val todoItemDao: TodoItemDao,
    private val tagDao: TagDao,
) : TodoRepository {
    //TodoItem
    override fun getTodoItems(): Flow<List<TodoItem>> {
        return todoItemDao.getTodoItems()
    }

    override fun getTodoItemsWithTags(): Flow<List<TodoItemWithTags>> {
        return todoItemDao.getTodoItemsWithTags()
    }

    override suspend fun getTodoItemById(id: Int): TodoItem? {
        return todoItemDao.getTodoItemById(id)
    }

    override suspend fun getTodoItemWithTagsById(id: Int): TodoItemWithTags? {
        return todoItemDao.getTodoItemWithTagsById(id)
    }

    override suspend fun insertTodoItem(todoItem: TodoItem) : Long {
        return todoItemDao.insertTodoItem(todoItem)
    }

    override suspend fun deleteTodoItem(todoItem: TodoItem) {
        return todoItemDao.deleteTodoItem(todoItem)
    }

    override suspend fun updateTodoItem(todoItem: TodoItem) {
        return todoItemDao.updateTodoItem(todoItem)
    }

    //Tag
    override fun getTags(): Flow<List<Tag>> {
        return tagDao.getTags()
    }

    override fun getTagsWithTodoItems(): Flow<List<TagWithTodoItems>> {
        return tagDao.getTagsWithTodoItems()
    }

    override suspend fun getTagById(id: Int): Tag? {
        return tagDao.getTagById(id)
    }

    override suspend fun getTagWithTodoItemsById(id: Int): TagWithTodoItems? {
        return tagDao.getTagWithTodoItemsById(id)
    }

    override suspend fun addTagToTodoItem(tag: Tag, todoItemId: Int) {
        tagDao.addTagToTodoItem(tag, todoItemId)
    }

    override suspend fun deleteTodoItemTag(tagId: Int, todoItemId: Int) {
        return tagDao.deleteTodoItemTag(tagId, todoItemId)
    }
}