package com.m4.notes.ui.interfaces

import com.m4.notes.data.models.NoteModel

interface OnItemClick {

    fun onLongClick(noteModel: NoteModel, position: Int)

    fun onClick(noteModel: NoteModel)
}