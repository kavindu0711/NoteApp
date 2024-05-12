// NotesAdapter.kt
package com.example.noteapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.NotesDatabaseHelper
import com.example.noteapp.databinding.NoteItemBinding

class NotesAdapter(private var notes: List<Note>, private val context: Context, private val db: NotesDatabaseHelper) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(private val binding: NoteItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTextView: TextView = binding.titleTextView
        val contentTextView: TextView = binding.contentTextView
        val updateButton: ImageView = binding.updateButton
        val deleteButton: ImageView = binding.deleteButton
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content

        holder.updateButton.setOnClickListener {
            val intent = Intent(context, UpdateNoteActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            val deletedNote = notes[position]
            db.deleteNote(deletedNote.id)
            refreshData(db.getAllNotes())
            Toast.makeText(context, "Note Deleted", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}