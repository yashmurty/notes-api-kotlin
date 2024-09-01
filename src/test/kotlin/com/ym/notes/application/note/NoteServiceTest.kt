package com.ym.notes.application.note

import com.ym.notes.domain.note.Note
import com.ym.notes.domain.note.NoteRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NoteServiceTest {

    private lateinit var noteRepository: NoteRepository
    private lateinit var noteService: NoteService

    @BeforeEach
    fun setUp() {
        noteRepository = mock()
        noteService = NoteService(noteRepository)
    }

    @Test
    fun `getAllNotes should return list of NoteResponses`() {
        val notes = listOf(
            Note(1, "Title 1", "Content 1", 1000L, 1000L),
            Note(2, "Title 2", "Content 2", 2000L, 2000L)
        )
        whenever(noteRepository.findAll()).thenReturn(notes)

        val result = noteService.getAllNotes()

        assertEquals(2, result.size)
        assertEquals(NoteResponse(1, "Title 1", "Content 1", 1000L, 1000L), result[0])
        assertEquals(NoteResponse(2, "Title 2", "Content 2", 2000L, 2000L), result[1])
    }

    @Test
    fun `getNoteById should return NoteResponse when note exists`() {
        val note = Note(1, "Title", "Content", 1000L, 1000L)
        whenever(noteRepository.findById(1)).thenReturn(note)

        val result = noteService.getNoteById(1)

        assertEquals(NoteResponse(1, "Title", "Content", 1000L, 1000L), result)
    }

    @Test
    fun `getNoteById should return null when note does not exist`() {
        whenever(noteRepository.findById(1)).thenReturn(null)

        val result = noteService.getNoteById(1)

        assertNull(result)
    }

    @Test
    fun `createNote should save and return new note`() {
        val command = CreateNoteCommand("New Title", "New Content")
        val savedNote = Note(1, "New Title", "New Content", 1000L, 1000L)
        whenever(noteRepository.save(any())).thenReturn(savedNote)

        val result = noteService.createNote(command)

        assertEquals(NoteResponse(1, "New Title", "New Content", 1000L, 1000L), result)
        verify(noteRepository).save(argThat {
            title == "New Title" && content == "New Content"
        })
    }

    @Test
    fun `updateNote should update and return note when it exists`() {
        val existingNote = Note(1, "Old Title", "Old Content", 1000L, 1000L)
        val command = UpdateNoteCommand("New Title", "New Content")
        val updatedNote = Note(1, "New Title", "New Content", 1000L, 2000L)
        whenever(noteRepository.findById(1)).thenReturn(existingNote)
        whenever(noteRepository.save(any())).thenReturn(updatedNote)

        val result = noteService.updateNote(1, command)

        assertEquals(NoteResponse(1, "New Title", "New Content", 1000L, 2000L), result)
        verify(noteRepository).save(argThat {
            id == 1 && title == "New Title" && content == "New Content"
        })
    }

    @Test
    fun `updateNote should return null when note does not exist`() {
        val command = UpdateNoteCommand("New Title", "New Content")
        whenever(noteRepository.findById(1)).thenReturn(null)

        val result = noteService.updateNote(1, command)

        assertNull(result)
        verify(noteRepository, never()).save(any())
    }

    @Test
    fun `deleteNote should return true when note is deleted`() {
        whenever(noteRepository.deleteById(1)).thenReturn(true)

        val result = noteService.deleteNote(1)

        assertTrue(result)
    }

    @Test
    fun `deleteNote should return false when note is not deleted`() {
        whenever(noteRepository.deleteById(1)).thenReturn(false)

        val result = noteService.deleteNote(1)

        assertFalse(result)
    }
}
