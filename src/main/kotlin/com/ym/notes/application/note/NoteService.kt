package com.ym.notes.application.note

import com.ym.notes.domain.note.Note
import com.ym.notes.domain.note.NoteRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class NoteService(private val noteRepository: NoteRepository) {
    fun getAllNotes(): List<Note> = noteRepository.findAll()

    fun getNoteById(id: Int): Note? = noteRepository.findById(id)

    fun createNote(command: CreateNoteCommand): NoteResponse {
        val note = noteRepository.save(
            Note(
                title = command.title,
                content = command.content
            )
        )
        return note.toResponse()
    }

    fun updateNote(id: Int, updatedNote: Note): Note? {
        return noteRepository.findById(id)?.let { existingNote ->
            val updatedNoteWithTimestamp = existingNote.copy(
                title = updatedNote.title,
                content = updatedNote.content,
                updatedAt = Instant.now().toEpochMilli()
            )
            noteRepository.save(updatedNoteWithTimestamp)
        }
    }

    fun deleteNote(id: Int): Boolean {
        return noteRepository.deleteById(id)
    }
}
