package com.ym.notes

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class NoteService(private val noteRepository: NoteRepository) {
    fun getAllNotes(): List<Note> = noteRepository.findAll()

    fun getNoteById(id: Int): Note? = noteRepository.findByIdOrNull(id)

    fun createNote(note: Note): Note = noteRepository.save(note)

    fun updateNote(id: Int, updatedNote: Note): Note? {
        return noteRepository.findByIdOrNull(id)?.let { existingNote ->
            val note = existingNote.copy(title = updatedNote.title, content = updatedNote.content)
            noteRepository.save(note)
        }
    }

    fun deleteNote(id: Int): Boolean {
        return noteRepository.findByIdOrNull(id)?.let {
            noteRepository.deleteById(id)
            true
        } ?: false
    }
}
