package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.findFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.todoapp.R
import com.example.todoapp.databinding.FragmentCreateTodoBinding
import com.example.todoapp.model.Todo
import com.example.todoapp.util.NotificationHelper
import com.example.todoapp.util.TodoWorker
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.TimeUnit

class CreateTodoFragment : Fragment(), RadioButtonListener, ButtonAddTodoClickListener {
    private lateinit var detailViewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        /*
        val btnAdd = view.findViewById<Button>(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val inputTitle = view.findViewById<TextInputEditText>(R.id.inputTitle)
            val inputNotes = view.findViewById<TextInputEditText>(R.id.inputNotes)
            var radio =
                view.findViewById<RadioButton>(view.findViewById<RadioGroup>(R.id.rbgPriority).checkedRadioButtonId)

            var todo = Todo(
                title = inputTitle.text.toString(),
                notes = inputNotes.text.toString(),
                priority = radio.tag.toString().toInt(),
                is_done = 0
            )
            val list = listOf(todo)
            detailViewModel.addTodo(list)

            Toast.makeText(view.context, "ToDo Added", Toast.LENGTH_LONG).show()

//            Notification
            NotificationHelper(view.context).createNotification("ToDo \"${inputTitle.text.toString()}\" Created", "A new ToDo has been created! Stay focus!")


            val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                this means that the notification will show 30 seconds after work queued
                .setInitialDelay(30, TimeUnit.SECONDS)
                .setInputData(workDataOf(
//                    Key-value pairs is an InputData object that constructed from the key & value pair. Left part is the key and the right part is the value
                    "title" to "ToDo Created",
                    "message" to "A new ToDo has been created! Stay focus!"))
                .build()

//            enqueue is where the work request is being queued. It will be registered on the android system and 30 seconds later will launch the notification
            WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)

            Navigation.findNavController(it).popBackStack()
        }
        */

        with(dataBinding) {
            todo = Todo("", "", 3, 0, 1)
            radioListener = this@CreateTodoFragment
            addListener = this@CreateTodoFragment
        }
    }

    override fun onRadioClick(view: View, priority: Int, todo: Todo) {
        todo.priority = priority
    }

    override fun onAddTodoClick(view: View) {
        val list = listOf(dataBinding.todo!!)
        detailViewModel.addTodo(list)

        Toast.makeText(view.context, "ToDo Added", Toast.LENGTH_LONG).show()

        val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                this means that the notification will show 30 seconds after work queued
            .setInitialDelay(10, TimeUnit.SECONDS)
            .setInputData(
                workDataOf(
//                    Key-value pairs is an InputData object that constructed from the key & value pair. Left part is the key and the right part is the value
                    "title" to "ToDo Created",
                    "message" to "A new ToDo has been created! Stay focus!"
                )
            )
            .build()

//            enqueue is where the work request is being queued. It will be registered on the android system and 30 seconds later will launch the notification
        WorkManager.getInstance(requireContext()).enqueue(myWorkRequest)
        Navigation.findNavController(view).popBackStack()

    }
}