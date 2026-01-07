package com.pradheep.roomdb.view.screens

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pradheep.roomdb.database.repository.NoteRepository
import com.pradheep.roomdb.database.roomdb.NoteDatabase

import com.pradheep.roomdb.databinding.ActivityMainBinding
import com.pradheep.roomdb.view.adapters.NoteAdapter
import com.pradheep.roomdb.viewmodel.note_view_model.NoteViewModel
import com.pradheep.roomdb.viewmodel.note_view_model.NoteViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding :ActivityMainBinding
    private lateinit var viewModel : NoteViewModel
    private lateinit var noteList :RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteList = binding.recyclerView

        // view model
        val noteDeo = NoteDatabase.getDatabase(application).noteDao()

        val repo= NoteRepository(noteDeo)

        val factory = NoteViewModelFactory(repo)

        viewModel = ViewModelProvider(this,factory).get(NoteViewModel::class.java)

        // set up recycler

        val adapter = NoteAdapter()

        noteList.adapter = adapter
        noteList.layoutManager = LinearLayoutManager(this)

        // observals
        viewModel.allNotes.observe(this){ notes->

            notes?.let { adapter.submitList(it) }

        }

        // navigate
        binding.headingNotes.setOnClickListener {
            val intent =Intent(this,AddEditNoteActivity::class.java)
            startActivity(intent)
        }


    }
}