package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.model.Todo

class TodoListAdapter(val todoList: ArrayList<Todo>, val adapterOnClick: (Int, Int) -> Unit) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.todo_item_layout, parent, false)

        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        var checkTask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checkTask.text = "${todoList[position].title} - priority: ${todoList[position].priority}"

        holder.view.findViewById<ImageView>(R.id.imgEdit).setOnClickListener {
            val action = TodoListFragmentDirections.actionEditTodo(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
            when (isChecked) {
                true -> adapterOnClick(1, todoList[position].uuid)
                false -> adapterOnClick(0, todoList[position].uuid)
            }
        }
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

}