package com.example.simpletodo.todoitem.data.source

import androidx.room.*
import com.example.simpletodo.todoitem.domain.model.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {
    @Query("SELECT * FROM tag")
    fun getTags(): Flow<List<Tag>>

    @Transaction
    @Query("SELECT * FROM tag")
    fun getTagsWithTodoItems(): Flow<List<TagWithTodoItems>>

    @Query("SELECT * FROM tag WHERE tagId = :id")
    suspend fun getTagById(id: Int): Tag?

    @Query("SELECT * FROM tag WHERE name = :name")
    suspend fun getTagByName(name: String): Tag

    @Query("SELECT * FROM tag WHERE tagId = :id")
    suspend fun getTagWithTodoItemsById(id: Int): TagWithTodoItems?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTag(tag: Tag) : Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTags(vararg tags: Tag) : List<Long>

    /*
    @Transaction
    suspend fun addTagsToTodoItem(vararg tags: Tag, todoItemId: Int) {
        val tagIds = insertTags(*tags)
        //Convert tags to tag ids
        addTodoItemTagsRelations(todoItemId, tagIds)
    }*/

    @Transaction
    suspend fun addTagToTodoItem(tag: Tag, todoItemId: Int) {
        val tagId = insertTag(tag)
        addTodoItemTagRelation(todoItemId, tagId.toInt())
    }

    //TODO: Add query to get todo item/tag relations


    @Query("INSERT INTO todo_item_tag (todoItemId, tagId) VALUES(:todoItemId, :tagId)")
    suspend fun addTodoItemTagRelation(todoItemId: Int, tagId: Int)

    @Transaction
    suspend fun addTodoItemTagsRelations(todoItemId: Int, vararg tagIds: Int) {
        for(tagId in tagIds) {
            addTodoItemTagRelation(todoItemId, tagId)
        }
    }

    @Transaction
    suspend fun deleteTodoItemTag(tagId: Int, todoItemId: Int) {
        deleteTodoItemTagRelation(tagId, todoItemId)
        deleteTagIfNoCrossRefs(tagId)
    }

    @Query("DELETE FROM todo_item_tag WHERE tagId = :tagId AND todoItemId = :todoItemId")
    suspend fun deleteTodoItemTagRelation(tagId: Int, todoItemId: Int)

    @Query("DELETE FROM tag WHERE\n" +
            "NOT EXISTS (SELECT todoItemId FROM todo_item_tag WHERE tagId = :tagId)")
    suspend fun deleteTagIfNoCrossRefs(tagId: Int)

    @Query("SELECT EXISTS (SELECT * FROM tag WHERE tagId = :tagId)")
    suspend fun getTagExistsById(tagId: Int) : Boolean
}