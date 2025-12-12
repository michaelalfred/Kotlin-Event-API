package com.example.uasmobileprogramming.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uasmobileprogramming.R
import com.example.uasmobileprogramming.UpdateEventActivity
import com.example.uasmobileprogramming.model.Event

class EventAdapter(
    private val list: MutableList<Event>
) : RecyclerView.Adapter<EventAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val t1: TextView = view.findViewById(R.id.txtTitle)
        val t2: TextView = view.findViewById(R.id.txtDate)
        val t3: TextView = view.findViewById(R.id.txtLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val e = list[position]
        holder.t1.text = e.title ?: "-"
        holder.t2.text = e.date ?: "-"
        holder.t3.text = e.location ?: "-"

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, UpdateEventActivity::class.java)

            // Kirim semua field, terutama ID
            intent.putExtra("id", e.id.toString())
            intent.putExtra("title", e.title)
            intent.putExtra("date", e.date)
            intent.putExtra("time", e.time)
            intent.putExtra("location", e.location)
            intent.putExtra("description", e.description)
            intent.putExtra("capacity", e.capacity.toString())
            intent.putExtra("status", e.status)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setData(newList: List<Event>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }
}

