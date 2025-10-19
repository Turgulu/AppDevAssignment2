package com.vu.s4660013_assignment2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import s4660013_Assignment2.R
import s4660013_Assignment2.data.network.EntityItem

class DashboardAdapter(
    private val items: List<EntityItem>,
    private val onItemClick: (EntityItem) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.EntityViewHolder>() {

    inner class EntityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvProperty1: TextView = itemView.findViewById(R.id.tvProperty1)
        val tvProperty2: TextView = itemView.findViewById(R.id.tvProperty2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntityViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return EntityViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntityViewHolder, position: Int) {
        val item = items[position]
        holder.tvProperty1.text = item.property1
        holder.tvProperty2.text = item.property2
        holder.itemView.setOnClickListener { onItemClick(item) }
    }

    override fun getItemCount() = items.size
}