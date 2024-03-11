package com.example.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo)

    @Query("select * from todo where is_done=0 order by priority desc")
    suspend fun selectAllTodo(): List<Todo>

    @Query("select * from todo where uuid=:id")
    fun selectTodo(id: Int): Todo

    @Query("update todo set title=:title, notes=:notes, priority=:priority where uuid=:id")
    suspend fun update(title: String, notes: String, priority: Int, id: Int)

    @Query("update todo set is_done=:is_done where uuid=:id")
    suspend fun isDone(is_done: Int, id: Int)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    fun deleteTodo(todo: Todo)
}