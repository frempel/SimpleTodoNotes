package com.example.simpletodo.todoitem.navigation

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Screen : Parcelable {

    @Parcelize
    object TodoItemsScreen : Screen()

    @Parcelize
    data class AddEditTodoItemScreen(val todoItemId: Int?): Screen()
}

