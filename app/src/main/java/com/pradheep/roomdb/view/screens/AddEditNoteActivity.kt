package com.pradheep.roomdb.view.screens

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pradheep.roomdb.R
import com.pradheep.roomdb.databinding.ActivityAddEditNoteBinding

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddEditNoteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}