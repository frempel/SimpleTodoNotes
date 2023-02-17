package com.example.simpletodo.todoitem.domain.use_case.tag

import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.model.TagWithTodoItems
import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class GetTagWithTodoItems(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): TagWithTodoItems? {
        return repository.getTagWithTodoItemsById(id)
    }
}