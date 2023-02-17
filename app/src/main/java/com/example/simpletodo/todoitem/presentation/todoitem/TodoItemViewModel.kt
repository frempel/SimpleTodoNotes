package com.example.simpletodo.todoitem.presentation.todoitem

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.use_case.TodoItemUseCases
import com.example.simpletodo.todoitem.domain.utils.OrderDirection
import com.example.simpletodo.todoitem.domain.utils.TodoItemOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoItemViewModel @Inject constructor(
    private val todoItemUseCases: TodoItemUseCases
): ViewModel() {

    private val _state = mutableStateOf(TodoItemState())
    val state: State<TodoItemState> = _state;

    private var cachedTodoItems = listOf<TodoItem>()
    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery
    private val _isSearchBarVisible = mutableStateOf(false)
    val isSearchBarVisible: State<Boolean> = _isSearchBarVisible
    private val _isSearching = mutableStateOf(false)
    val isSearching: State<Boolean> = _isSearching
    private var isSearchStarting: Boolean = true

    private var lastDeletedTodoItem: TodoItem? = null
    private var getTodoItemsJob: Job? = null

    init {
        getTodoItems(TodoItemOrder.Timestamp(OrderDirection.Descending))
    }

    private fun searchTodoItems(query: String) {
        val todoItems = if (isSearchStarting) {
            state.value.todoItems
        }
        else {
            cachedTodoItems
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                _isSearching.value = false
                isSearchStarting = true

                //Fixes a bug where tapping the search button then pressing the back button for cancelling the search would empty the list
                if (cachedTodoItems.isNotEmpty()) {
                    _state.value = state.value.copy(
                        todoItems = cachedTodoItems,
                        todoItemOrder = state.value.todoItemOrder)
                }
                return@launch
            }
            val results = todoItems.filter {
                it.title.contains(query.trim(), ignoreCase = true) ||
                        it.description.contains(query.trim(), ignoreCase = true)
            }
            if (isSearchStarting) {
                cachedTodoItems = state.value.todoItems
                isSearchStarting = false
            }

            //Write the results to the displayed list of items
            _state.value = state.value.copy(
                todoItems = results,
                todoItemOrder = state.value.todoItemOrder
            )
            _isSearching.value = true
        }
    }

    fun onEvent(event: TodoItemEvent) {
        when (event) {
            is TodoItemEvent.Order -> {
                if(
                    state.value.todoItemOrder::class == event.todoItemOrder::class &&
                    state.value.todoItemOrder.orderDirection == event.todoItemOrder.orderDirection
                )
                    return
                getTodoItems(event.todoItemOrder)
            }
            is TodoItemEvent.DeleteTodoItem -> {
                viewModelScope.launch {
                    todoItemUseCases.deleteTodoItem(event.todoItem)
                    lastDeletedTodoItem = event.todoItem
                }
            }
            is TodoItemEvent.RestoreTodoItem -> {
                viewModelScope.launch {
                    todoItemUseCases.addTodoItem(lastDeletedTodoItem ?: return@launch)
                    lastDeletedTodoItem = null
                }
            }
            is TodoItemEvent.UpdateTodoItem -> {
                viewModelScope.launch {
                    todoItemUseCases.updateTodoItem(event.todoItem)
                }
            }
            is TodoItemEvent.ShowSearchBar -> {
                _isSearchBarVisible.value = true
            }
            is TodoItemEvent.HideSearchBar -> {
                _isSearchBarVisible.value = false
            }
            is TodoItemEvent.SearchTodoItems -> {
                _searchQuery.value = event.query
                searchTodoItems(event.query)
            }
        }
    }

    private fun getTodoItems(todoItemOrder: TodoItemOrder) {
        getTodoItemsJob?.cancel()

        getTodoItemsJob = todoItemUseCases.getTodoItems(todoItemOrder)
            .onEach {
                todoItems -> _state.value = state.value.copy(
                    todoItems = todoItems,
                    todoItemOrder = todoItemOrder
                )
            }.launchIn(viewModelScope)
    }
}