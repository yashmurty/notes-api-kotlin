package com.ym.notes.domain.note

interface NoteRepository {
    fun findAll(): List<Note>
    fun findById(id: Int): Note?
    fun save(note: Note): Note
    fun deleteById(id: Int): Boolean
    fun existsById(id: Int): Boolean
}
