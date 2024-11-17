package com.m4.notes.ui.fragments.note

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.m4.notes.App
import com.m4.notes.R
import com.m4.notes.data.models.NoteModel
import com.m4.notes.databinding.CustomDialogBinding

import com.m4.notes.databinding.FragmentNoteBinding
import com.m4.notes.ui.adapters.NoteAdapter
import com.m4.notes.ui.interfaces.OnItemClick
import com.m4.notes.utils.PreferenceHelper


class NoteFragment : Fragment(), OnItemClick{

    private lateinit var binding: FragmentNoteBinding
    private lateinit var noteAdapter: NoteAdapter
    private val sharedPreferences = PreferenceHelper()
    private var isLinearLayout = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences.unit(requireContext())
        isLinearLayout = sharedPreferences.isLinearLayout
        noteAdapter = NoteAdapter(this, this, isLinearLayout)
        initialize()
        setupListeners()
        getData()
    }

    private fun getData() {
        App.appDatabase?.noteDao()?.getAll()?.observe(viewLifecycleOwner){ list ->
            noteAdapter.submitList(list)
        }
    }

    private fun initialize() {
        binding.apply {
            if (isLinearLayout){
                rvNote.layoutManager = LinearLayoutManager(requireContext())
            } else {
            rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
        }
            rvNote.adapter = noteAdapter
        }

    }

    private fun setupListeners() = with(binding) {
        btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_noteFragment_to_noteDetailFragment)
        }

        btnChangeView.setOnClickListener{
            isLinearLayout = !isLinearLayout
            if (isLinearLayout){
                rvNote.layoutManager = LinearLayoutManager(requireContext())
            } else {
                rvNote.layoutManager = GridLayoutManager(requireContext(), 2)
            }
            noteAdapter.setLinearLayout(isLinearLayout)
            sharedPreferences.isLinearLayout = isLinearLayout

        }
    }

    override fun onLongClick(noteModel: NoteModel, position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        val view = CustomDialogBinding.inflate(layoutInflater)
        builder.setView(view.root)
        val alertDialog = builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.btnDismiss.setOnClickListener {
            alertDialog.dismiss()
        }
        view.btnConfirm.setOnClickListener {
            alertDialog.dismiss()
            App.appDatabase?.noteDao()?.deleteNote(noteModel)
        }
        alertDialog.show()
    }

    override fun onClick(noteModel: NoteModel) {
        val action = NoteFragmentDirections.actionNoteFragmentToNoteDetailFragment(noteModel.id)
        findNavController().navigate(action)
    }

}

