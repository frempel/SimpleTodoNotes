package com.example.simpletodo.todoitem.domain.utils

sealed class TodoItemOrder (val orderDirection: OrderDirection) {
    class Title(orderDirection: OrderDirection): TodoItemOrder(orderDirection)
    class Description(orderDirection: OrderDirection): TodoItemOrder(orderDirection)
    class Timestamp(orderDirection: OrderDirection): TodoItemOrder(orderDirection)

    fun oppositeDirection(): TodoItemOrder {
        return when(this) {
            is Title -> Title(!this.orderDirection)
            is Description -> Description(!this.orderDirection)
            is Timestamp -> Timestamp(!this.orderDirection)
        }
    }
}
