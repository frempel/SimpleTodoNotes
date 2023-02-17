package com.example.simpletodo.todoitem.presentation.todoitem

import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder

sealed class TodoItemEvent {
    data class Order(val todoItemOrder: TodoItemOrder): TodoItemEvent()
    data class DeleteTodoItem(val todoItem: TodoItem): TodoItemEvent()
    object RestoreTodoItem: TodoItemEvent()
    data class UpdateTodoItem(val todoItem: TodoItem):TodoItemEvent()
    object ShowSearchBar: TodoItemEvent()
    object HideSearchBar: TodoItemEvent()
    data class SearchTodoItems(val query: String): TodoItemEvent()
}
