package com.example.simpletodo.todoitem.domain.use_case

import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import com.example.simpletodo.todoitem.domain.utils.OrderDirection
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodoItems(private val repository: TodoRepository) {
    operator fun invoke(order: TodoItemOrder = TodoItemOrder.Timestamp(OrderDirection.Descending)): Flow<List<TodoItem>> {
        return repository.getTodoItems().map { todoItems ->
            when(order.orderDirection) {
                is OrderDirection.Ascending -> {
                    when (order) {
                        is TodoItemOrder.Title -> todoItems.sortedBy { it.title.lowercase() }
                        is TodoItemOrder.Description -> todoItems.sortedBy { it.description.lowercase() }
                        is TodoItemOrder.Timestamp -> todoItems.sortedBy { it.timestamp }
                    }
                }
                is OrderDirection.Descending -> {
                    when (order) {
                        is TodoItemOrder.Title -> todoItems.sortedByDescending { it.title.lowercase() }
                        is TodoItemOrder.Description -> todoItems.sortedByDescending { it.description.lowercase() }
                        is TodoItemOrder.Timestamp -> todoItems.sortedByDescending { it.timestamp }
                    }
                }
            }
        }
    }
}