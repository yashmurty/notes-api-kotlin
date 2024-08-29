package com.ym.notes.application.note

data class UpdateNoteCommand(
    val title: String? = null,
    val content: String? = null
)
