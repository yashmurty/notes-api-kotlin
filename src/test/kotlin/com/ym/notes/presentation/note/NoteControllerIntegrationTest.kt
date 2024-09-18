package com.ym.notes.presentation.note

import com.fasterxml.jackson.databind.ObjectMapper
import com.ym.notes.application.note.CreateNoteCommand
import com.ym.notes.application.note.UpdateNoteCommand
import com.ym.notes.domain.note.Note
import com.ym.notes.domain.note.NoteRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.transaction.annotation.Transactional
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class NoteControllerIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var noteRepository: NoteRepository

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    companion object {
        @Container
        private val postgresContainer = PostgreSQLContainer<Nothing>("postgres:16")

        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", postgresContainer::getJdbcUrl)
            registry.add("spring.datasource.username", postgresContainer::getUsername)
            registry.add("spring.datasource.password", postgresContainer::getPassword)
        }
    }

    @Test
    fun `should create a note`() {
        val command = CreateNoteCommand("Test Title", "Test Content")

        mockMvc.post("/api/notes") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(command)
        }.andExpect {
            status { isCreated() }
            jsonPath("$.title") { value("Test Title") }
            jsonPath("$.content") { value("Test Content") }
        }
    }

    @Test
    fun `should get all notes`() {
        val note1 = Note(title = "Title 1", content = "Content 1")
        val note2 = Note(title = "Title 2", content = "Content 2")
        noteRepository.save(note1)
        noteRepository.save(note2)

        entityManager.flush()

        mockMvc.get("/api/notes")
            .andExpect {
                status { isOk() }
                jsonPath("$", hasSize<Any>(2))
                jsonPath("$[0].title") { value("Title 1") }
                jsonPath("$[1].title") { value("Title 2") }
            }
    }

    @Test
    fun `should get note by id`() {
        val note = noteRepository.save(Note(title = "Test Title", content = "Test Content"))
        val id = note.id ?: throw IllegalStateException("Note ID should not be null after saving")

        mockMvc.perform(get("/api/notes/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("Test Title"))
            .andExpect(jsonPath("$.content").value("Test Content"))
    }

    @Test
    fun `should update note`() {
        val note = noteRepository.save(Note(title = "Old Title", content = "Old Content"))
        val id = note.id ?: throw IllegalStateException("Note ID should not be null after saving")
        val command = UpdateNoteCommand("New Title", "New Content")

        mockMvc.perform(
            put("/api/notes/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(command))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.title").value("New Title"))
            .andExpect(jsonPath("$.content").value("New Content"))
    }

    @Test
    fun `should delete note`() {
        val note = noteRepository.save(Note(title = "Test Title", content = "Test Content"))
        val id = note.id ?: throw IllegalStateException("Note ID should not be null after saving")

        mockMvc.perform(delete("/api/notes/$id"))
            .andExpect(status().isNoContent)

        assertFalse(noteRepository.existsById(id))
    }
}
