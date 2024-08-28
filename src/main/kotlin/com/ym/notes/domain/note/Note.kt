package com.ym.notes.domain.note

import java.time.Instant

data class Note(
    val id: Int? = null,
    val title: String,
    val content: String,
    val createdAt: Long = Instant.now().toEpochMilli(),
    val updatedAt: Long = Instant.now().toEpochMilli()
) {
    init {
        require(title.isNotBlank()) { "Title cannot be blank" }
        require(content.isNotBlank()) { "Content cannot be blank" }
    }

    fun updateContent(newContent: String): Note {
        require(newContent.isNotBlank()) { "Content cannot be blank" }
        return copy(content = newContent, updatedAt = Instant.now().toEpochMilli())
    }

    fun updateTitle(newTitle: String): Note {
        require(newTitle.isNotBlank()) { "Title cannot be blank" }
        return copy(title = newTitle, updatedAt = Instant.now().toEpochMilli())
    }

    companion object {
        fun create(title: String, content: String): Note {
            return Note(title = title, content = content)
        }
    }
}
