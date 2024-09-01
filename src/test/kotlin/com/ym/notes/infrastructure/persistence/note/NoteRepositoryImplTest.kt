package com.ym.notes.infrastructure.persistence.note

import com.ym.notes.domain.note.Note
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class NoteRepositoryImplTest {

    private lateinit var jpaRepository: NoteJpaRepository
    private lateinit var repository: NoteRepositoryImpl

    @BeforeEach
    fun setup() {
        jpaRepository = mock()
        repository = NoteRepositoryImpl(jpaRepository)
    }

    @Test
    fun `findAll should return list of domain models`() {
        val entities = listOf(
            NoteEntity(1, "Title 1", "Content 1", 1000L, 1000L),
            NoteEntity(2, "Title 2", "Content 2", 2000L, 2000L)
        )
        whenever(jpaRepository.findAll()).thenReturn(entities)

        val result = repository.findAll()

        assertEquals(2, result.size)
        assertEquals(Note(1, "Title 1", "Content 1", 1000L, 1000L), result[0])
        assertEquals(Note(2, "Title 2", "Content 2", 2000L, 2000L), result[1])
    }

    @Test
    fun `findById should return domain model when entity exists`() {
        val entity = NoteEntity(1, "Title", "Content", 1000L, 1000L)
        whenever(jpaRepository.findById(1)).thenReturn(Optional.of(entity))

        val result = repository.findById(1)

        assertEquals(Note(1, "Title", "Content", 1000L, 1000L), result)
    }

    @Test
    fun `findById should return null when entity does not exist`() {
        whenever(jpaRepository.findById(1)).thenReturn(Optional.empty())

        val result = repository.findById(1)

        assertNull(result)
    }

    @Test
    fun `save should persist entity and return domain model`() {
        val note = Note(null, "Title", "Content", 1000L, 1000L)
        val savedEntity = NoteEntity(1, "Title", "Content", 1000L, 1000L)
        whenever(jpaRepository.save(any())).thenReturn(savedEntity)

        val result = repository.save(note)

        assertEquals(Note(1, "Title", "Content", 1000L, 1000L), result)
        verify(jpaRepository).save(any())
    }

    @Test
    fun `deleteById should return true when entity exists`() {
        whenever(jpaRepository.existsById(1)).thenReturn(true)

        val result = repository.deleteById(1)

        assertTrue(result)
        verify(jpaRepository).deleteById(1)
    }

    @Test
    fun `deleteById should return false when entity does not exist`() {
        whenever(jpaRepository.existsById(1)).thenReturn(false)

        val result = repository.deleteById(1)

        assertFalse(result)
        verify(jpaRepository, never()).deleteById(1)
    }

    @Test
    fun `existsById should return true when entity exists`() {
        whenever(jpaRepository.existsById(1)).thenReturn(true)

        val result = repository.existsById(1)

        assertTrue(result)
    }

    @Test
    fun `existsById should return false when entity does not exist`() {
        whenever(jpaRepository.existsById(1)).thenReturn(false)

        val result = repository.existsById(1)

        assertFalse(result)
    }
}
