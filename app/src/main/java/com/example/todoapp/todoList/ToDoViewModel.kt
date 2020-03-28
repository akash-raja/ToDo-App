package com.example.todoapp.todoList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.todoapp.createTodo.TodoDatabase
import com.example.todoapp.createTodo.TodoNote
import com.example.todoapp.createTodo.TodoRepository
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TodoRepository
    val noteList: LiveData<List<TodoNote>>

    init {
        val database = TodoDatabase.getDatabase(application)
        repository = TodoRepository(database.toDoDao())
        noteList = (repository.allNotes)
    }

    fun removeNote(id: Int) {
        viewModelScope.launch {
            repository.deleteNote(id)
        }
    }
}