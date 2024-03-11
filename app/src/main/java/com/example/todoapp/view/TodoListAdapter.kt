package com.example.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.databinding.TodoItemLayoutBinding
import com.example.todoapp.model.Todo

class TodoListAdapter(val todoList: ArrayList<Todo>, val adapterOnClick: (Int, Int) -> Unit) :
    RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(), TodoCheckedChangeListener, TodoEditClicked {
    class TodoViewHolder(var view: TodoItemLayoutBinding) : RecyclerView.ViewHolder(view.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<TodoItemLayoutBinding>(
            inflater,
            R.layout.todo_item_layout,
            parent,
            false
        )
//        val view = TodoItemLayoutBinding.inflate(inflater, parent, false)

        return TodoViewHolder(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.todo = todoList[position]
        holder.view.checkedChangeListener = this
        holder.view.editListener = this
        /*var checkTask = holder.view.findViewById<CheckBox>(R.id.checkTask)
        checkTask.text = "${todoList[position].title}  -  ${todoList[position].notes}  -  [${todoList[position].priority}]"

        holder.view.findViewById<ImageView>(R.id.imgEdit).setOnClickListener {
            val action = TodoListFragmentDirections.actionEditTodo(todoList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }

        checkTask.setOnCheckedChangeListener { compoundButton, isChecked ->
            when (isChecked) {
                true -> adapterOnClick(1, todoList[position].uuid)
                false -> adapterOnClick(0, todoList[position].uuid)
            }
        }*/
    }

    fun updateTodoList(newTodoList: List<Todo>) {
        todoList.clear()
        todoList.addAll(newTodoList)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, todo: Todo) {
        when (isChecked) {
            true -> adapterOnClick(1, todo.uuid)
            false -> adapterOnClick(0, todo.uuid)
        }
    }

    override fun onClick(view: View) {
        val uuid = view.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodo(uuid)
        Navigation.findNavController(view).navigate(action)
    }

}