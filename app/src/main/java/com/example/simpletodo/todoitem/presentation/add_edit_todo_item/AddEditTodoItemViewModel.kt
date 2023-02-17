package com.example.simpletodo.todoitem.presentation.add_edit_todo_item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.simpletodo.R
import com.example.simpletodo.todoitem.UiText
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.model.TodoItemBlankTitleException
import com.example.simpletodo.todoitem.domain.use_case.TodoItemUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoItemViewModel @Inject constructor(
    private val todoItemUseCases: TodoItemUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    companion object {
        const val NAV_ARG_TODO_ITEM_ID = "nav_arg_add_edit_todo_item_view_model_todo_item_id"
    }

    private val _title = mutableStateOf(TextFieldState())
    val title: State<TextFieldState> = _title

    private val _description = mutableStateOf(TextFieldState())
    val description: State<TextFieldState> = _description

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoItemId: Int? = null

    init {
        savedStateHandle.get<Int>(NAV_ARG_TODO_ITEM_ID)?.let { todoItemId ->
            if (todoItemId != -1) {
                viewModelScope.launch {
                    todoItemUseCases.getTodoItem(todoItemId)?.also { todoItem ->
                        currentTodoItemId = todoItem.id

                        _title.value = title.value.copy(
                            text = todoItem.title,
                            isFocused = title.value.isFocused
                        )
                        _description.value = description.value.copy(
                            text = todoItem.description,
                            isFocused = description.value.isFocused
                        )
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoItemEvent) {
        when (event) {
            is AddEditTodoItemEvent.EnteredTitle -> {
                _title.value = _title.value.copy(
                    text = event.title,
                    isFocused = title.value.isFocused
                )
            }
            is AddEditTodoItemEvent.TitleChangedFocus -> {
                _title.value = title.value.copy(
                    text = title.value.text,
                    isFocused = !event.focusState.isFocused
                )
            }
            is AddEditTodoItemEvent.EnteredDescription -> {
                _description.value = description.value.copy(
                    text = event.description,
                    isFocused = description.value.isFocused
                )
            }
            is AddEditTodoItemEvent.DescriptionChangedFocus -> {
                _description.value = description.value.copy(
                    text = description.value.text,
                    isFocused = !event.focusState.isFocused
                )
            }
            is AddEditTodoItemEvent.SaveTodoItem -> {
                viewModelScope.launch {
                    try {
                        todoItemUseCases.addTodoItem(
                            TodoItem(
                                id = currentTodoItemId,
                                title = title.value.text,
                                description = description.value.text,
                                timestamp = System.currentTimeMillis()
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTodoItem)
                    }
                    catch (e: TodoItemBlankTitleException) {
                        _eventFlow.emit(UiEvent.ShowSnackbar(
                            message = UiText.StringResource(resId = R.string.todo_item_empty_title_message)
                        ))
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: UiText): UiEvent()
        object SaveTodoItem: UiEvent()
    }

}