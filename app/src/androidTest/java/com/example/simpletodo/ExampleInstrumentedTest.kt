package com.example.simpletodo

import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.simpletodo.todoitem.data.source.TagDao
import com.example.simpletodo.todoitem.data.source.TodoDatabase
import com.example.simpletodo.todoitem.data.source.TodoItemDao
import com.example.simpletodo.todoitem.domain.model.Tag
import com.example.simpletodo.todoitem.domain.model.TagBlankNameException
import com.example.simpletodo.todoitem.domain.model.TodoItem
import com.example.simpletodo.todoitem.domain.use_case.TodoItemUseCases
import com.example.simpletodo.todoitem.domain.use_case.tag.TagUseCases
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import java.io.IOException
import java.util.concurrent.Executors
import javax.inject.Inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
//@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ExampleInstrumentedTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var db: TodoDatabase
    private lateinit var todoItemDao: TodoItemDao
    private lateinit var tagDao : TagDao

    @Before
    fun injectDependencies() {
        hiltRule.inject()
    }

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            TodoDatabase::class.java
        )
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .allowMainThreadQueries()
            .build()
        todoItemDao = db.todoItemDao
        tagDao = db.tagDao
        //repository = TodoRepositoryImpl(db.todoItemDao, db.tagDao)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun addTodoItemShouldPass() = runTest {
        val todoItem = TodoItem(
            id = null,
            title = "Add Tag Test",
            description = "Test description.",
            timestamp = System.currentTimeMillis()
        )
        val todoItemId = todoItemDao.insertTodoItem(todoItem).toInt()

        assertEquals(todoItemDao.getTodoItemById(todoItemId)!!.title, "Add Tag Test")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun addTodoItemShouldNotInsert() = runTest {
        val todoItem = TodoItem(
            id = null,
            title = " ",
            description = "Test description.",
            timestamp = System.currentTimeMillis()
        )
        val todoItemId = todoItemDao.insertTodoItem(todoItem).toInt()

        assert(todoItemDao.getTodoItemExistsById(todoItemId))
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    @Throws(Exception::class)
    fun addTagShouldPass() = runTest {
        val todoItem = TodoItem(
            id = null,
            title = "Test Title",
            description = "Test description.",
            timestamp = System.currentTimeMillis()
        )
        val todoItemId = todoItemDao.insertTodoItem(todoItem).toInt()
        tagDao.addTagToTodoItem(Tag(null, "test"), todoItemId)

        assertEquals(tagDao.getTags().count(), 1)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun addTagBlankNameShouldNotInsert() = runTest {
        val todoItem = TodoItem(
            id = null,
            title = "Test Title",
            description = "Test description.",
            timestamp = System.currentTimeMillis()
        )
        val tag = Tag(null, "");
        val todoItemId = todoItemDao.insertTodoItem(todoItem).toInt()
        tagDao.addTagToTodoItem(tag, todoItemId)

        assertEquals(tagDao.getTags().count(), 0)
    }
}