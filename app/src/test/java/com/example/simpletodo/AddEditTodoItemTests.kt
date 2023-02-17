package com.example.simpletodo

import androidx.room.Room
import com.example.simpletodo.todoitem.data.repository.TodoRepositoryImpl
import com.example.simpletodo.todoitem.data.source.TodoDatabase
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import com.example.simpletodo.todoitem.domain.use_case.AddTodoItem
import com.example.simpletodo.todoitem.domain.use_case.TodoItemUseCases
import org.junit.Test

class AddEditTodoItemTests {

    /*
    @Test
    fun `empty title throws TodoItemBlankTitleException`() {
        val repository = TodoRepositoryImpl(db.todoItemDao)
        val useCases = TodoItemUseCases(addTodoItem = AddTodoItem(TodoRepository))

        val result = todoItemUseCases.addTodoItem(
            TodoItem(
                id = currentTodoItemId,
                title = title.value.text,
                description = description.value.text,
                timestamp = System.currentTimeMillis()
            )
        )
    }*/
}