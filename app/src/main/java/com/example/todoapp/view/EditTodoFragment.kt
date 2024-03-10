package com.example.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.R
import com.example.todoapp.viewmodel.DetailTodoViewModel
import com.google.android.material.textfield.TextInputEditText

class EditTodoFragment : Fragment() {
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

        view.findViewById<TextView>(R.id.txtHeader).text = "Edit ToDo"
        view.findViewById<Button>(R.id.btnAdd).text = "Save Changes"

        val uuid = EditTodoFragmentArgs.fromBundle(requireArguments()).uuid
        detailViewModel.fetch(uuid)

        observeViewModel()
    }

    private fun observeViewModel() {
        detailViewModel.todoLD.observe(viewLifecycleOwner, Observer {
            view?.findViewById<TextInputEditText>(R.id.inputTitle)?.setText(it.title)
            view?.findViewById<TextInputEditText>(R.id.inputNotes)?.setText(it.notes)
        })
    }
}