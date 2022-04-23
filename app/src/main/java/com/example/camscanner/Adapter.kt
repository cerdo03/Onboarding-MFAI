package com.example.camscanner

import android.content.Context
import android.provider.ContactsContract
import android.sax.EndTextElementListener
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter(private val context:Context,private val listener: Lstner): RecyclerView.Adapter<Adapter.ViewHolder>() {
    val allDocument=ArrayList<Document>()
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val textView=itemView.findViewById<TextView>(R.id.textView)
        val deleteButton=itemView.findViewById<ImageView>(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        val viewHolder= ViewHolder(view)
        viewHolder.deleteButton.setOnClickListener{
            listener.dltDocument(allDocument[viewHolder.adapterPosition])
        }
        viewHolder.textView.setOnClickListener {
            listener.itemClicked(allDocument[viewHolder.adapterPosition])
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentNote=allDocument[position]
        holder.textView.text=currentNote.title
    }

    override fun getItemCount(): Int {
        return allDocument.size
    }
    fun updateList(newList: List<Document>){
        allDocument.clear()
        allDocument.addAll(newList)
        notifyDataSetChanged()
    }
}
interface Lstner{
    fun dltDocument(document: Document)
    fun itemClicked(document: Document)
}