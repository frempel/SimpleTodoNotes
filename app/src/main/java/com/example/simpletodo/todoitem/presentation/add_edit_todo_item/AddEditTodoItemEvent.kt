package com.example.simpletodo.todoitem.presentation.add_edit_todo_item

import androidx.compose.ui.focus.FocusState

sealed class AddEditTodoItemEvent {
    data class EnteredTitle(val title: String) : AddEditTodoItemEvent()
    data class TitleChangedFocus(val focusState: FocusState) : AddEditTodoItemEvent()
    data class EnteredDescription(val description: String) : AddEditTodoItemEvent()
    data class DescriptionChangedFocus(val focusState: FocusState) : AddEditTodoItemEvent()
    object SaveTodoItem: AddEditTodoItemEvent()
}