package com.example.simpletodo.todoitem.domain.use_case

import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class GetTodoItem(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): TodoItem? {
        return repository.getTodoItemById(id)
    }
}