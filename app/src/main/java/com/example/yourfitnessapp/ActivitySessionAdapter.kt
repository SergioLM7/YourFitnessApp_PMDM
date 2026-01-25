package com.example.yourfitnessapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yourfitnessapp.model.ActivitySession

class ActivitySessionAdapter (private val items: MutableList<ActivitySession> = mutableListOf()) :
    RecyclerView.Adapter<ActivitySessionAdapter.VH>(){
        class VH(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val tvActivity: TextView = itemView.findViewById(R.id.tvActivityName)
            val tvDuration: TextView = itemView.findViewById(R.id.tvDuration)
            val tvType: TextView = itemView.findViewById(R.id.tvType)
            val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {

            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_activity_session, parent, false)

            return VH(view)
        }

        override fun onBindViewHolder(holder: VH, position: Int) {
            val item = items[position]

            holder.tvActivity.text = item.activityName
            holder.tvDuration.text = item.duration
            holder.tvType.text = item.type
            holder.tvDate.text = item.date
        }

        override fun getItemCount(): Int = items.size

        fun summitList(newItems:List<ActivitySession>) {
            items.clear()
            items.addAll(newItems)
            notifyDataSetChanged()
        }
}