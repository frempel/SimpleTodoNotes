package com.example.simpletodo.todoitem.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class TodoItemWithTags(
    @Embedded val todoItem: TodoItem,
    @Relation(
        //TodoItem (id, title, description...)
        parentColumn = "todoItemId",
        //Tag
        entityColumn = "tagId",
        associateBy = Junction(TodoItemTagCrossRef::class)
    )
    val tags: List<Tag>
)

data class TagWithTodoItems(
    @Embedded val tag: Tag,
    @Relation(
        //Tag
        parentColumn = "tagId",
        //TodoItem
        entityColumn = "todoItemId",
        associateBy = Junction(TodoItemTagCrossRef::class)
    )
    val playlists: List<TodoItem>
)