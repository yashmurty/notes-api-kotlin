package com.ym.notes.infrastructure.persistence.note

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "notes")
class NoteEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, length = 300)
    val title: String,

    @Column(nullable = false, length = 10000)
    val content: String,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
