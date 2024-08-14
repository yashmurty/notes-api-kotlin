package com.ym.notes.domain.note

import java.time.LocalDateTime

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(title.isNotBlank()) { "Title cannot be blank" }
        require(content.isNotBlank()) { "Content cannot be blank" }
    }

    fun updateContent(newContent: String): Note {
        require(newContent.isNotBlank()) { "Content cannot be blank" }
        return copy(content = newContent, updatedAt = LocalDateTime.now())
    }

    fun updateTitle(newTitle: String): Note {
        require(newTitle.isNotBlank()) { "Title cannot be blank" }
        return copy(title = newTitle, updatedAt = LocalDateTime.now())
    }

    companion object {
        fun create(title: String, content: String): Note {
            return Note(title = title, content = content)
        }
    }
}
