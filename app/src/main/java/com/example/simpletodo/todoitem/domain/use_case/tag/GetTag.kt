package com.example.simpletodo.todoitem.domain.use_case.tag

import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class GetTag(private val repository: TodoRepository) {
    suspend operator fun invoke(id: Int): Tag? {
        return repository.getTagById(id)
    }
}