package com.vu.s4660013_assignment2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vu.s4660013_assignment2.R
import com.vu.s4660013_assignment2.data.EntityItem

class DashboardAdapter(
    private var entityList: List<EntityItem> = emptyList(),
    private val onItemClick: (EntityItem) -> Unit
) : RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.entityName)
        val descriptionTextView: TextView = itemView.findViewById(R.id.entityDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_entity, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val entity = entityList[position]

        holder.nameTextView.text = entity.name
        holder.descriptionTextView.text = entity.description

        holder.itemView.setOnClickListener {
            onItemClick(entity)
        }
    }

    override fun getItemCount(): Int = entityList.size

    fun updateData(newList: List<EntityItem>) {
        entityList = newList
        notifyDataSetChanged()
    }
}