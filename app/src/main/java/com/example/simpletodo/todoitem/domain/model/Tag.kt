package com.example.simpletodo.todoitem.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "tag",
    indices = [
        Index(value = ["name"],
        unique = true)
    ]
)
data class Tag(
    @PrimaryKey
    @ColumnInfo(name = "tagId")
    val id: Int? = null,
    val name: String,
)

class TagBlankNameException: Exception()
