package com.example.simpletodo.todoitem.domain.use_case

import com.example.simpletodo.todoitem.domain.use_case.tag.*

data class TodoItemUseCases(
    val getTodoItems: GetTodoItems,
    val getTodoItemsWithTags: GetTodoItemsWithTags,
    val getTodoItem: GetTodoItem,
    val getTodoItemWithTags: GetTodoItemWithTags,
    val deleteTodoItem: DeleteTodoItem,
    val addTodoItem: AddTodoItem,
    val updateTodoItem: UpdateTodoItem,
    val addTag: AddTag,
    val deleteTag: DeleteTag,
    val getTag: GetTag,
    val getTags: GetTags,
    val getTagsWithTodoItems: GetTagsWithTodoItems,
    val getTagWithTodoItems: GetTagWithTodoItems,
)
