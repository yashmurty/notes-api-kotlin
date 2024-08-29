package com.ym.notes.presentation.note

import com.ym.notes.application.note.CreateNoteCommand
import com.ym.notes.application.note.NoteResponse
import com.ym.notes.application.note.NoteService
import com.ym.notes.application.note.UpdateNoteCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/notes")
class NoteController(private val noteService: NoteService) {
    @GetMapping
    fun getAllNotes(): ResponseEntity<List<NoteResponse>> = ResponseEntity.ok(noteService.getAllNotes())

    @GetMapping("/{id}")
    fun getNoteById(@PathVariable id: Int): ResponseEntity<NoteResponse> {
        val response = noteService.getNoteById(id)
        return response?.let { ResponseEntity.ok(response) } ?: ResponseEntity.notFound().build()
    }

    @PostMapping
    fun createNote(@RequestBody command: CreateNoteCommand): ResponseEntity<NoteResponse> {
        val response = noteService.createNote(command)
        return ResponseEntity(response, HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun updateNote(@PathVariable id: Int, @RequestBody command: UpdateNoteCommand): ResponseEntity<NoteResponse> {
        val response = noteService.updateNote(id, command)
        return response?.let { ResponseEntity.ok(response) } ?: ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
    fun deleteNote(@PathVariable id: Int): ResponseEntity<Void> {
        val deleted = noteService.deleteNote(id)
        return if (deleted) ResponseEntity.noContent().build() else ResponseEntity.notFound().build()
    }
}
