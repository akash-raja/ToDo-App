package com.example.todoapp.todoList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.createTodo.TodoNote
import kotlinx.android.synthetic.main.todo_item.view.*

class ToDoAdapter(private val context: Context, private val listener: (TodoNote) -> Unit) :
    RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var list = listOf<TodoNote>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.todo_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    fun setToDoList(newList: List<TodoNote>) {
        list = newList
        notifyDataSetChanged()
    }

    fun getIdForNoteAtPosition(adapterPosition: Int): Int {
        return list[adapterPosition].id
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(note: TodoNote, clickListener: (TodoNote) -> Unit) {

            itemView.todo.text = note.toDoNote.trim()
            itemView.title.text = note.toDoTitle.trim()
            itemView.setOnClickListener { clickListener(note) }

            if (note.toDoTitle.isEmpty()) {
                itemView.title.visibility = GONE
            } else {
                itemView.title.visibility = VISIBLE

            }

            if (note.toDoNote.isEmpty()) {
                itemView.todo.visibility = GONE
            } else {
                itemView.todo.visibility = VISIBLE
            }
        }
    }
}
