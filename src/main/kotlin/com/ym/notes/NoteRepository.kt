package com.ym.notes

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NoteRepository : JpaRepository<Note, Int> {
    // You can add custom query methods here if needed
}
