package com.example.yourfitnessapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessapp.model.ActivitySession

/**
 * Clase para el adapter del RecyclerView
 */
class ActivitySessionAdapter (private val items: MutableList<ActivitySession> = mutableListOf()) :
    RecyclerView.Adapter<ActivitySessionAdapter.VH>() {
        class VH(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val tvActivity: TextView = itemView.findViewById(R.id.tvActivityName)
            val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
            val tvType: TextView = itemView.findViewById(R.id.tvType)
            val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        }

    /**
     * Método que se ejecuta al crear el ViewHolder
     * @param parent ViewGroup en el que se creará el ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_activity_session, parent, false)

        return VH(view)
    }

    /**
     * Método que se ejecuta al vincular el ViewHolder con los datos
     * @param holder ViewHolder que se vinculará con los datos
     */
    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]

        holder.tvActivity.text = item.activityName
        holder.tvDuration.text = item.duration
        holder.tvType.text = item.type
        holder.tvDate.text = item.date
    }

    /**
     * Método que devuelve el número de elementos del RecyclerView
     * @return Int con el número de elementos
     */
    override fun getItemCount(): Int = items.size

    /**
     * Método que añade un elemento individual al RecyclerView
     * @param newItem ActivitySession que se añadirá
     */
    fun addItem(newItem: ActivitySession) {
        items.add(newItem)
        notifyItemInserted(items.size - 1)
    }
}