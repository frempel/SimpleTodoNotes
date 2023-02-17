package com.example.simpletodo.todoitem.presentation.todoitem

import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.utils.OrderDirection
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder

data class TodoItemState(
    val todoItems: List<TodoItem> = emptyList(),
    val todoItemOrder: TodoItemOrder = TodoItemOrder.Timestamp(OrderDirection.Descending)
)
