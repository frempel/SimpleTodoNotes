package com.example.simpletodo.todoitem.presentation.todoitem

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf

data class SearchState(
    val searchQuery: String = "",
    val isSearchBarVisible: Boolean = false,
    val isSearching: Boolean = false,
    val isSearchStarting: Boolean = true
)