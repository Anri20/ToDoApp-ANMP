package com.example.todoapp.view

import android.widget.CompoundButton
import com.example.todoapp.model.Todo

interface TodoCheckChangeListener{
    fun onCheckChanged(cb: CompoundButton, isChecked: Boolean, todo: Todo)
}