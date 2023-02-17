package com.example.simpletodo.dependency_injection

import android.app.Application
import androidx.room.Room
import com.example.simpletodo.todoitem.data.repository.TodoRepositoryImpl
import com.example.simpletodo.todoitem.data.source.TodoDatabase
import com.example.simpletodo.todoitem.domain.repository.TodoRepository
import com.example.simpletodo.todoitem.domain.use_case.*
import com.example.simpletodo.todoitem.domain.use_case.tag.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoItemModule {
    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DATABASE_NAME)
            //.fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db: TodoDatabase): TodoRepository {
        return TodoRepositoryImpl(db.todoItemDao, db.tagDao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repository: TodoRepository): TodoItemUseCases {
        return TodoItemUseCases(
            getTodoItems = GetTodoItems(repository),
            getTodoItemsWithTags = GetTodoItemsWithTags(repository),
            getTodoItem = GetTodoItem(repository),
            getTodoItemWithTags = GetTodoItemWithTags(repository),
            deleteTodoItem = DeleteTodoItem(repository),
            addTodoItem = AddTodoItem(repository),
            updateTodoItem = UpdateTodoItem(repository),
            getTags = GetTags(repository),
            getTag = GetTag(repository),
            addTag = AddTag(repository),
            deleteTag = DeleteTag(repository),
            getTagsWithTodoItems = GetTagsWithTodoItems(repository),
            getTagWithTodoItems = GetTagWithTodoItems(repository)
        )
    }

    /*
    @Provides
    @Singleton
    fun provideTagUseCases(repository: TodoRepository): TagUseCases {
        return TagUseCases(
            getTags = GetTags(repository),
            getTag = GetTag(repository),
            addTag = AddTag(repository),
            deleteTag = DeleteTag(repository),
            getTagsWithTodoItems = GetTagsWithTodoItems(repository)
        )
    }*/
}