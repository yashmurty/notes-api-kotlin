package com.ym.notes

import com.ym.notes.application.note.CreateNoteCommand
import com.ym.notes.application.note.NoteResponse
import com.ym.notes.application.note.NoteService
import com.ym.notes.domain.note.Note
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/notes")
class NoteController(private val noteService: NoteService) {
    @GetMapping
    fun getAllNotes(): List<Note> = noteService.getAllNotes()

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Int): ResponseEntity<Note> {
        val note = noteService.getNoteById(id)
        return note?.let { ResponseEntity.ok(note) } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createNote(@RequestBody command: CreateNoteCommand): ResponseEntity<NoteResponse> {
        val response = noteService.createNote(command)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Int, @RequestBody updatedNote: Note): ResponseEntity<Note> {
        val note = noteService.updateNote(id, updatedNote)
        return note?.let { ResponseEntity.ok(note) } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = noteService.deleteNote(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }
}
