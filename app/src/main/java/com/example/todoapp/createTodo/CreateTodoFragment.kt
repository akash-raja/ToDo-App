package com.example.todoapp.createTodo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import kotlinx.android.synthetic.main.fragment_create_todo.*

class CreateTodoFragment : Fragment() {

    private val args: CreateTodoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewModel = ViewModelProvider(this).get(CreateTodoViewModel::class.java)
        viewModel.setNote(args.note)

        viewModel.editNoteLiveData.observe(viewLifecycleOwner, Observer {
            todo_title.setText(it.toDoTitle)
            todo_note.setText(it.toDoNote)

            todo_title.setSelection(it.toDoTitle.length)


        })

        viewModel.showToast.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, "Empty Note Discarded", Toast.LENGTH_SHORT).show()
        })

        toolbar.setNavigationOnClickListener {
            viewModel.insertNote(
                TodoNote(
                    args.note?.id ?: 0,
                    todo_title.text.toString(),
                    todo_note.text.toString()
                )
            )
            findNavController().popBackStack()
        }
    }
}
