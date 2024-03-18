package com.example.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.example.todoapp.model.Todo

interface TodoCheckedChangeListener {
    fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, todo: Todo)
}

interface TodoEditClicked {
    fun onClick(view: View)
}

interface RadioButtonListener {
    fun onRadioClick(view: View, priority: Int, todo: Todo)
}

interface TodoSaveChangesListener {
    fun onClickSaveChange(view: View, todo: Todo)
}

interface ButtonAddTodoClickListener {
    fun onAddTodoClick(view: View)
}

interface DateClickListener {
    fun onDateCLick(view: View)
}

interface TimeClickListener {
    fun onTimeCLick(view: View)
}