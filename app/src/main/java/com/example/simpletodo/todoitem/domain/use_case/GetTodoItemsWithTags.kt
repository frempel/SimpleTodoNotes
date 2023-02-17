package com.example.simpletodo.todoitem.domain.use_case

import com.example.simpletodo.todoitem.domain.model.TodoItemWithTags
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import com.example.simpletodo.todoitem.domain.utils.OrderDirection
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodoItemsWithTags(private val repository: TodoRepository) {
    operator fun invoke(order: TodoItemOrder = TodoItemOrder.Timestamp(OrderDirection.Descending)): Flow<List<TodoItemWithTags>> {
        return repository.getTodoItemsWithTags().map { todoItems ->
            when(order.orderDirection) {
                is OrderDirection.Ascending -> {
                    when (order) {
                        is TodoItemOrder.Title -> todoItems.sortedBy { it.todoItem.title.lowercase() }
                        is TodoItemOrder.Description -> todoItems.sortedBy { it.todoItem.description.lowercase() }
                        is TodoItemOrder.Timestamp -> todoItems.sortedBy { it.todoItem.timestamp }
                    }
                }
                is OrderDirection.Descending -> {
                    when (order) {
                        is TodoItemOrder.Title -> todoItems.sortedByDescending { it.todoItem.title.lowercase() }
                        is TodoItemOrder.Description -> todoItems.sortedByDescending { it.todoItem.description.lowercase() }
                        is TodoItemOrder.Timestamp -> todoItems.sortedByDescending { it.todoItem.timestamp }
                    }
                }
            }
        }
    }
}