package com.example.simpletodo.todoitem.domain.use_case

import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.model.TodoItemBlankTitleException
import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class AddTodoItem(private val repository: TodoRepository) {

    @Throws(TodoItemBlankTitleException::class)
    suspend operator fun invoke(todoItem: TodoItem) {
        if (todoItem.title.isBlank()) {
            throw TodoItemBlankTitleException()
        }
        repository.insertTodoItem(todoItem)
    }
}