package com.example.todoapp.todoList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import kotlinx.android.synthetic.main.fragment_add_to_do.*

class ToDoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_to_do, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val viewModel = ViewModelProvider(this).get(ToDoViewModel::class.java)
        val toDoAdapter = ToDoAdapter(context!!) {
            findNavController().navigate(
                ToDoFragmentDirections.actionAddToDoFragmentToCreateTodoFragment(it)
            )
        }
        recycler_view_todo.adapter = toDoAdapter

        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val id = toDoAdapter.getIdForNoteAtPosition(viewHolder.adapterPosition)
                viewModel.removeNote(id)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recycler_view_todo)

        viewModel.noteList.observe(viewLifecycleOwner, Observer {
            toDoAdapter.setToDoList(it)
        })

        addTodo.setOnClickListener {
            findNavController().navigate(R.id.action_addToDoFragment_to_createTodoFragment)
        }
    }

}
