package com.m4.notes.ui.adapters



import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup


import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


import com.m4.notes.data.models.NoteModel
import com.m4.notes.databinding.GridItemNoteBinding
import com.m4.notes.databinding.ItemNoteBinding


import com.m4.notes.ui.interfaces.OnItemClick
import com.m4.notes.utils.PreferenceHelper

class NoteAdapter(
    private val onLongClick : OnItemClick,
    private val onClick : OnItemClick,
    private var isLinearLayout : Boolean
) : ListAdapter<NoteModel, RecyclerView.ViewHolder>(DiffCallback()) {


    class LinearViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel) = with(binding){
            tvTitle.text = item.title
            tvDesc.text = item.description
            tvDate.text = item.date
            cvNote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(item.color)))
        }
    }

    class GridViewHolder(private val binding: GridItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: NoteModel) = with(binding){
            tvTitle.text = item.title
            tvDesc.text = item.description
            tvDate.text = item.date
            cvNote.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(item.color)))
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (isLinearLayout) 1 else 0
    }

    fun setLinearLayout(isLinearLayout: Boolean) {
        this.isLinearLayout = isLinearLayout
    }

    class DiffCallback : DiffUtil.ItemCallback<NoteModel>() {
        override fun areItemsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: NoteModel, newItem: NoteModel): Boolean {
            return oldItem.id == newItem.id
        }

    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LinearViewHolder) {
            holder.bind(getItem(position))
        } else if (holder is GridViewHolder) {
            holder.bind(getItem(position))
        }
        holder.itemView.setOnLongClickListener {
            onLongClick.onLongClick(getItem(holder.adapterPosition), holder.adapterPosition)
            true
        }
        holder.itemView.setOnClickListener {
            onClick.onClick(getItem(holder.adapterPosition))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == 1) {
            LinearViewHolder(
                ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        } else {
            GridViewHolder(
                GridItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}
