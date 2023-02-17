package com.example.simpletodo.todoitem.domain.use_case.tag

import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTags(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<Tag>> {
        return repository.getTags().map { tags ->
            tags.sortedByDescending { it.name.lowercase() }
        }
    }
}