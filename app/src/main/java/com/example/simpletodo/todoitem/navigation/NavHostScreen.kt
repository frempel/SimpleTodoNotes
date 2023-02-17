package com.example.simpletodo.todoitem.navigation

import androidx.compose.runtime.Composable
import com.example.simpletodo.todoitem.presentation.add_edit_todo_item.AddEditTodoItemScreen
import com.example.simpletodo.todoitem.presentation.todoitem.TodoItemsScreen
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.rememberNavController

@Composable
fun NavHostScreen() {
    val navController = rememberNavController<Screen>(startDestination = Screen.TodoItemsScreen)
    NavBackHandler(controller = navController)

    NavHost(navController) { screen ->
        when (screen) {
            is Screen.TodoItemsScreen -> TodoItemsScreen(navController)
            is Screen.AddEditTodoItemScreen -> AddEditTodoItemScreen(navController, screen.todoItemId)
        }
    }
}