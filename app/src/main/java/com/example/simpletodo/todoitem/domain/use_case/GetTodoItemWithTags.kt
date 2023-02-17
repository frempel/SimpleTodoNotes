package com.example.simpletodo.todoitem.domain.use_case

import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.model.TodoItemWithTags
import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class GetTodoItemWithTags(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): TodoItemWithTags? {
        return repository.getTodoItemWithTagsById(id)
    }
}