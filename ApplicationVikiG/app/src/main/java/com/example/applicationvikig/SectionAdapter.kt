package com.example.applicationvikig

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationvikig.databinding.ItemSectionBinding

class SectionAdapter(
    private val sections: List<MovieSection>,
    private val onMovieCLick: (Movie) -> Unit
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    class SectionViewHolder(val binding: ItemSectionBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = sections[position]

        holder.binding.sectionTitle.text = section.title

        holder.binding.sectionRecycler.layoutManager =
            LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)


        holder.binding.sectionRecycler.adapter = MovieAdapter(section.movies, onMovieCLick)
    }

    override fun getItemCount() = sections.size
}