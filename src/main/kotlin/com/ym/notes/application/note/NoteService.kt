package com.ym.notes.application.note

import com.ym.notes.domain.note.Note
import com.ym.notes.domain.note.NoteRepository
import org.springframework.stereotype.Service

@Service
class NoteService(private val noteRepository: NoteRepository) {
    fun getAllNotes(): List<NoteResponse> = noteRepository.findAll().map { it.toResponse() }

    fun getNoteById(id: Int): NoteResponse? = noteRepository.findById(id)?.toResponse()

    fun createNote(command: CreateNoteCommand): NoteResponse {
        val note = noteRepository.save(
            Note(
                title = command.title,
                content = command.content,
            )
        )
        return note.toResponse()
    }

    fun updateNote(id: Int, command: UpdateNoteCommand): NoteResponse? {
        return noteRepository.findById(id)?.let { existingNote ->
            val updatedNote = applyUpdates(existingNote, command)
            noteRepository.save(updatedNote).toResponse()
        }
    }

    fun deleteNote(id: Int): Boolean {
        return noteRepository.deleteById(id)
    }

    private fun applyUpdates(note: Note, command: UpdateNoteCommand): Note {
        var updatedNote = note
        command.title?.let { updatedNote = updatedNote.updateTitle(it) }
        command.content?.let { updatedNote = updatedNote.updateContent(it) }
        // Add more update logic here as needed
        return updatedNote
    }
}
