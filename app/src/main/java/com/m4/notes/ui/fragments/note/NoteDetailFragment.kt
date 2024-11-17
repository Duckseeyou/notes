package com.m4.notes.ui.fragments.note


import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.m4.notes.App
import com.m4.notes.data.models.NoteModel
import com.m4.notes.databinding.FragmentNoteDetailBinding
import java.text.SimpleDateFormat
import java.util.Date


class NoteDetailFragment : Fragment() {
    private var color = "#ffffff"
    private lateinit var binding: FragmentNoteDetailBinding
    private var noteId : Int = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateNote()
        setDate()
        setupListeners()

    }


    private fun updateNote() {

        arguments?.let { args ->
            noteId = args.getInt("noteId", -1)
        }

        if (noteId != -1){

            val clickedNote = App.appDatabase?.noteDao()?.getById(noteId)
            clickedNote?.let { note ->
                binding.etTitle.setText(note.title)
                binding.etDesc.setText(note.description)
                binding.tvDone.visibility = View.VISIBLE
                changeNoteColor(note.color)
            }
        }
    }


    private fun setDate() {
        val date = SimpleDateFormat("dd MMMM")
        val time = SimpleDateFormat("HH:mm")
        binding.tvDate.text = date.format(Date())
        binding.tvTime.text = time.format(Date())
    }


    private fun changeNoteColor(color: String) = with(binding){
        this@NoteDetailFragment.color = color
        cvColors.visibility = View.GONE
        bgChangeColor.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color)))
    }


    private fun setupListeners() = with(binding) {

        btnChangeColor.setOnClickListener {
            if (cvColors.visibility == View.GONE){
                cvColors.visibility = View.VISIBLE
            } else{
                cvColors.visibility = View.GONE
            }
        }

        btnYellow.setOnClickListener {
            changeNoteColor("#fff599")
        }

        btnPurple.setOnClickListener {
           changeNoteColor("#B69CFF")
        }

        btnPink.setOnClickListener {
            changeNoteColor("#fd99ff")
        }

        btnOrange.setOnClickListener {
            changeNoteColor("#ff9e9e")
        }

        btnGreen.setOnClickListener {
            changeNoteColor("#91f48f")
        }

        btnCyan.setOnClickListener {
            changeNoteColor("#9EFFFF")
        }

        btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        etTitle.addTextChangedListener {
            if (etTitle.text.isNotEmpty() && etDesc.text.isNotEmpty()){
                tvDone.visibility = View.VISIBLE
            } else {
                tvDone.visibility = View.GONE
            }
        }

        etDesc.addTextChangedListener {
            if (etTitle.text.isNotEmpty() && etDesc.text.isNotEmpty()){
                tvDone.visibility = View.VISIBLE
            } else {
                tvDone.visibility = View.GONE
            }
        }

        tvDone.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDesc.text.toString()
            val date = tvDate.text.toString() + " " + tvTime.text.toString()
            if (noteId != -1){
                val updatedNote = NoteModel(title, description, date, color)
                updatedNote.id = noteId
                App.appDatabase?.noteDao()?.updateNote(updatedNote)
            } else {
                App.appDatabase?.noteDao()?.insertNote(NoteModel(title, description, date, color))
            }
            findNavController().navigateUp()
        }
    }

}