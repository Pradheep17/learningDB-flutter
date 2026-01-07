package com.pradheep.roomdb.view.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pradheep.roomdb.databinding.NoteItemLayoutBinding
import com.pradheep.roomdb.model.response.Note
import com.pradheep.roomdb.view.screens.AddEditNoteActivity

class NoteAdapter : ListAdapter<Note, NoteAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Note>() {
            override fun areItemsTheSame(oldItem: Note, newItem: Note) = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Note, newItem: Note) = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(private val binding: NoteItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.titleTextView.text = note.title
            binding.descriptionTextView.text= note.description

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, AddEditNoteActivity::class.java).apply {
                    putExtra("NOTE_ID", note.id)
                    putExtra("NOTE_TITLE", note.title)
                    putExtra("NOTE_DESCRIPTION", note.description)
                }
                context.startActivity(intent)
            }
        }
    }
}
