package com.pradheep.roomdb.viewmodel.note_view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pradheep.roomdb.database.repository.NoteRepository
import com.pradheep.roomdb.model.response.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepo: NoteRepository): ViewModel() {

    val allNotes: LiveData<List<Note>> = noteRepo.allNotes

    fun insert(note: Note)= viewModelScope.launch {
        noteRepo.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        noteRepo.update(note)
    }

    fun delete(note: Note) =viewModelScope.launch {
        noteRepo.delete(note)
    }
}

class NoteViewModelFactory(private val repository: NoteRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
           return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}