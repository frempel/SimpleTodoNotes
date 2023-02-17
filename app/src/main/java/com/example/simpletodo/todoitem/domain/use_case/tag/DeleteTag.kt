package com.example.simpletodo.todoitem.domain.use_case.tag

import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class DeleteTag(private val repository: TodoRepository) {
    suspend operator fun invoke(tagId: Int, todoItemId: Int) {
        repository.deleteTodoItemTag(tagId, todoItemId)
    }
}