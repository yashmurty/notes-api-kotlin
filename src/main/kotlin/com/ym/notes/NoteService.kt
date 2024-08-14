package com.ym.notes

import com.ym.notes.domain.note.Note
import com.ym.notes.domain.note.NoteRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NoteService(private val noteRepository: NoteRepository) {
    fun getAllNotes(): List<Note> = noteRepository.findAll()

    fun getNoteById(id: Int): Note? = noteRepository.findById(id)

    fun createNote(note: Note): Note = noteRepository.save(note)

    fun updateNote(id: Int, updatedNote: Note): Note? {
        return noteRepository.findById(id)?.let { existingNote ->
            val updatedNoteWithTimestamp = existingNote.copy(
                title = updatedNote.title,
                content = updatedNote.content,
                updatedAt = LocalDateTime.now()
            )
            noteRepository.save(updatedNoteWithTimestamp)
        }
    }

    fun deleteNote(id: Int): Boolean {
        return noteRepository.deleteById(id)
    }
}
