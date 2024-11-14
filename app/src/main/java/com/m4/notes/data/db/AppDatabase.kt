package com.m4.notes.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.m4.notes.data.db.daos.NoteDao
import com.m4.notes.data.models.NoteModel

@Database(entities = [NoteModel::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun noteDao() : NoteDao
}