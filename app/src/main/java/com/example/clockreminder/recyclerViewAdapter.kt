package com.example.clockreminder

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class recyclerViewAdapter(private val context: Context,private val listener: INotesRVAdapter):
    RecyclerView.Adapter<recyclerViewAdapter.reminderViewHolder>() {
    val allReminder=ArrayList<reminder>()
    inner class reminderViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val text = itemView.findViewById<TextView>(R.id.text)
        val date = itemView.findViewById<TextView>(R.id.date)
        val dlt = itemView.findViewById<ImageView>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): reminderViewHolder {
        val viewHolder= reminderViewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
        viewHolder.dlt.setOnClickListener {
            listener.ItemDelete(allReminder[viewHolder.adapterPosition])
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: reminderViewHolder, position: Int) {
        val currentReminder = allReminder[position]
        holder.text.text =currentReminder.text
        holder.date.text=currentReminder.time
    }

    override fun getItemCount(): Int {
        return allReminder.size
    }

    fun updateList(newList: List<reminder>){
        allReminder.clear()
        allReminder.addAll(newList)
        notifyDataSetChanged()
    }

}
interface INotesRVAdapter{
    fun ItemDelete(reminder: reminder)
}