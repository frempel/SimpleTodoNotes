package com.example.simpletodo.todoitem.domain.use_case.tag

data class TagUseCases(
    val getTags: GetTags,
    val getTag: GetTag,
    val addTag: AddTag,
    val deleteTag: DeleteTag,
    val getTagsWithTodoItems: GetTagsWithTodoItems,
)
