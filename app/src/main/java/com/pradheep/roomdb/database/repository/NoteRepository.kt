package com.pradheep.roomdb.database.repository

import androidx.lifecycle.LiveData
import com.pradheep.roomdb.database.dao.NoteDAO
import com.pradheep.roomdb.model.response.Note

class NoteRepository(private val noteDAO: NoteDAO) {

    val allNotes: LiveData<List<Note>> = noteDAO.getAllNotes()

    suspend fun insert(note: Note){
        noteDAO.insert(note)
    }

    suspend fun update(note: Note){
        noteDAO.update(note)
    }

    suspend fun delete(note: Note){
        noteDAO.delete(note)
    }
}