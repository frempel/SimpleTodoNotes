package com.example.simpletodo.todoitem.domain.use_case.tag

import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.model.TagBlankNameException
import com.example.simpletodo.todoitem.domain.model.TodoItemBlankTitleException
import com.example.simpletodo.todoitem.domain.repository.TodoRepository

class AddTag(private val repository: TodoRepository) {
    @Throws(TodoItemBlankTitleException::class)
    suspend operator fun invoke(tag: Tag, todoItemId: Int) {
        if (tag.name.isBlank()) {
            throw TagBlankNameException()
        }
        repository.addTagToTodoItem(tag, todoItemId)
    }
}