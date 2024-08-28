package com.ym.notes.application.note

import com.ym.notes.domain.note.Note

data class NoteResponse(
    val id: Int,
    val title: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long
)

// You can also add an extension function here for mapping
fun Note.toResponse() = NoteResponse(
    id = id!!,
    title = title,
    content = content,
    createdAt = createdAt,
    updatedAt = updatedAt
)
