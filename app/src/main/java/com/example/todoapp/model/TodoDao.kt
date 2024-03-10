package com.example.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo)

    @Query("select * from todo")
    fun selectAllTodo(): List<Todo>

    @Query("select * from todo where uuid=:id")
    fun selectTodo(id: Int): Todo

    @Query("update todo set title=:title, notes=:notes, priority=:priority where uuid=:id")
    suspend fun update(title: String, notes: String, priority: Int, id: Int)

    @Delete
    fun deleteTodo(todo: Todo)
}