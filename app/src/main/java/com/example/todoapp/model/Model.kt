package com.example.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Todo(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "notes")
    var notes: String
) {
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}