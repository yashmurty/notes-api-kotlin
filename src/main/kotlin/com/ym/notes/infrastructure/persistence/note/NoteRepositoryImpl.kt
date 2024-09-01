package com.ym.notes.infrastructure.persistence.note

import com.ym.notes.domain.note.Note
import com.ym.notes.domain.note.NoteRepository
import org.springframework.stereotype.Repository

@Repository
class NoteRepositoryImpl(private val jpaRepository: NoteJpaRepository) : NoteRepository {

    override fun findAll(): List<Note> = jpaRepository.findAll().map { it.toDomainModel() }

    override fun findById(id: Int): Note? = jpaRepository.findById(id).orElse(null)?.toDomainModel()

    override fun save(note: Note): Note {
        val entity = note.toEntity()
        val savedEntity = jpaRepository.save(entity)
        return savedEntity.toDomainModel()
    }

    override fun deleteById(id: Int): Boolean {
        if (jpaRepository.existsById(id)) {
            jpaRepository.deleteById(id)
            return true
        }
        return false
    }

    override fun existsById(id: Int): Boolean = jpaRepository.existsById(id)
}

// Extension functions for mapping
private fun NoteEntity.toDomainModel() = Note(id, title, content, createdAt, updatedAt)
private fun Note.toEntity() = NoteEntity(id, title, content, createdAt, updatedAt)

// JPA Repository interface
interface NoteJpaRepository : org.springframework.data.jpa.repository.JpaRepository<NoteEntity, Int>
