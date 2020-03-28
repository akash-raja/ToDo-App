package com.example.todoapp.createTodo

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CreateTodoViewModel(application: Application) : AndroidViewModel(application) {

    private val database = TodoDatabase.getDatabase(application)
    private val repository = TodoRepository(database.toDoDao())
    val editNoteLiveData = MutableLiveData<TodoNote>()
    val showToast = MutableLiveData<Boolean>()

    fun insertNote(note: TodoNote) {
        if (note.toDoNote.isNotEmpty() || note.toDoTitle.isNotEmpty()) {
            viewModelScope.launch { repository.insertNote(note) }
        } else{
            showToast.value = true
        }
    }

    fun setNote(note: TodoNote?) {
        note?.let {
            editNoteLiveData.postValue(it)
        }
    }
}