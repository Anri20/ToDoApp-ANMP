package com.example.todoapp.view

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateFormat
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TimePicker
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
import java.util.Calendar
import java.util.concurrent.TimeUnit
import kotlin.math.min

class CreateTodoFragment : Fragment(), RadioButtonListener, ButtonAddTodoClickListener,
    DateClickListener, TimeClickListener, DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {
    private lateinit var detailViewModel: DetailTodoViewModel
    private lateinit var dataBinding: FragmentCreateTodoBinding

    var year = 0
    var month = 0
    var day = 0
    var hour = 0
    var minute = 0

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
            dateListener = this@CreateTodoFragment
            timeListener = this@CreateTodoFragment
        }
    }

    override fun onRadioClick(view: View, priority: Int, todo: Todo) {
        todo.priority = priority
    }

    override fun onAddTodoClick(view: View) {
        val c = Calendar.getInstance()
        c.set(year, month, day, hour, minute, 0)
        val today = Calendar.getInstance()
        val diff = (c.timeInMillis/1000L) - (today.timeInMillis/1000L)

        dataBinding.todo!!.todo_date = (c.timeInMillis/1000L).toInt()

        val list = listOf(dataBinding.todo!!)
        detailViewModel.addTodo(list)

        Toast.makeText(view.context, "ToDo Added", Toast.LENGTH_LONG).show()

        val myWorkRequest = OneTimeWorkRequestBuilder<TodoWorker>()
//                this means that the notification will show 30 seconds after work queued
            .setInitialDelay(diff, TimeUnit.SECONDS)
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

    override fun onDateCLick(view: View) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        activity?.let { it1 -> DatePickerDialog(it1, this, year, month, day).show() }
    }

    override fun onTimeCLick(view: View) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)
        TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity)).show()
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        Calendar.getInstance().let {
            it.set(year, month, day)
            dataBinding.txtDate.setText(
                day.toString().padStart(2, '0') + "-" + month.toString()
                    .padStart(2, '0') + "-" + year
            )
            this.year = year
            this.month = month
            this.day = day
        }
    }

    override fun onTimeSet(p0: TimePicker?, hour: Int, minute: Int) {
        dataBinding.txtTime.setText(hour.toString().padStart(2, '0') + ":" + minute.toString().padStart(2, '0'))
        this.hour = hour
        this.minute = minute
    }
}