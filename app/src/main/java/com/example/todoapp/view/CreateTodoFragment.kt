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
import com.example.todoapp.R
import com.example.todoapp.model.Todo
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.textfield.TextInputEditText

class CreateTodoFragment : Fragment() {
    private lateinit var detailViewModel: DetailTodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

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
            Navigation.findNavController(it).popBackStack()
        }
    }
}