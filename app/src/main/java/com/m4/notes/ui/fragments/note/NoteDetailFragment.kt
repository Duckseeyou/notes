package com.m4.notes.ui.fragments.note

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.m4.notes.App
import com.m4.notes.data.models.NoteModel


import com.m4.notes.databinding.FragmentNoteDetailBinding


class NoteDetailFragment : Fragment() {

    private lateinit var binding: FragmentNoteDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
    }

    private fun setupListeners() = with(binding) {
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
            App.appDatabase?.noteDao()?.insertNote(NoteModel(title, description))
            findNavController().navigateUp()
        }
    }
}