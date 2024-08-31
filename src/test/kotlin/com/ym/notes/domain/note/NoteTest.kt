package com.ym.notes.domain.note

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NoteTest {

    @Test
    fun `should create a valid note`() {
        val note = Note(title = "Test Title", content = "Test Content")

        assertEquals("Test Title", note.title)
        assertEquals("Test Content", note.content)
        assertNotNull(note.createdAt)
        assertNotNull(note.updatedAt)
    }

    @Test
    fun `should throw exception when creating note with blank title`() {
        assertThrows<IllegalArgumentException> {
            Note(title = "", content = "Test Content")
        }
    }

    @Test
    fun `should throw exception when creating note with blank content`() {
        assertThrows<IllegalArgumentException> {
            Note(title = "Test Title", content = "")
        }
    }

    @Test
    fun `should update content`() {
        val note = Note(title = "Test Title", content = "Test Content")
        Thread.sleep(10) // Small delay to ensure time difference
        val updatedNote = note.updateContent("New Content")

        assertEquals("New Content", updatedNote.content)
        assert(updatedNote.updatedAt > note.updatedAt)
    }

    @Test
    fun `should throw exception when updating with blank content`() {
        val note = Note(title = "Test Title", content = "Test Content")
        assertThrows<IllegalArgumentException> {
            note.updateContent("")
        }
    }

    @Test
    fun `should update title`() {
        val note = Note(title = "Test Title", content = "Test Content")
        Thread.sleep(10) // Small delay to ensure time difference
        val updatedNote = note.updateTitle("New Title")

        assertEquals("New Title", updatedNote.title)
        assert(updatedNote.updatedAt > note.updatedAt)
    }

    @Test
    fun `should throw exception when updating with blank title`() {
        val note = Note(title = "Test Title", content = "Test Content")
        assertThrows<IllegalArgumentException> {
            note.updateTitle("")
        }
    }

    @Test
    fun `should not change createdAt when updating`() {
        val note = Note(title = "Test Title", content = "Test Content")
        val updatedNote = note.updateContent("New Content").updateTitle("New Title")

        assertEquals(note.createdAt, updatedNote.createdAt)
    }
}
