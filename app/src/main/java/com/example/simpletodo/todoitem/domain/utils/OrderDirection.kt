package com.example.simpletodo.todoitem.domain.utils

sealed class OrderDirection {
    object Ascending: OrderDirection()
    object Descending: OrderDirection()

    operator fun not(): OrderDirection {
        return when(this) {
            is Ascending -> Descending
            is Descending -> Ascending
        }
    }
}
