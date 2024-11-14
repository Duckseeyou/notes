package com.m4.notes.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "noteModel")
data class NoteModel(
    val title: String,
    val description: String,
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
