package com.example.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.example.todoapp.model.Todo
import com.example.todoapp.model.TodoDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()
    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
//    Dispatchers tell you, on which thread should I run this block of code in (Main, IO, Default)

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
//        use launch function to create a job. The launch is known as fire and forget builder
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "newtododb")
                .build()
//            use postValue to asynchronously handle expected result from DAO
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }

    fun clearTask(todo: Todo) {
        launch {
            val db = Room.databaseBuilder(getApplication(), TodoDatabase::class.java, "newtododb")
                .build()
            db.todoDao().deleteTodo(todo)
            todoLD.postValue(db.todoDao().selectAllTodo())
        }
    }
}