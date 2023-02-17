package com.example.simpletodo.todoitem.domain.use_case.tag

import com.example.simpletodo.todoitem.domain.model.TagWithTodoItems
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTagsWithTodoItems(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<TagWithTodoItems>> {
        return repository.getTagsWithTodoItems().map { tags ->
            tags.sortedByDescending { it.tag.name.lowercase() }
        }
    }
}