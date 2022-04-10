package com.example.w1_p2_loanapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class adapter(private val context: Context,private val labels: ArrayList<field>,private val listener:inf) :RecyclerView.Adapter<adapter.viewHolder>(){
    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val label = itemView.findViewById<TextView>(R.id.label)
        val enterDetails  =itemView.findViewById<EditText>(R.id.enterDetails)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val viewHolder= viewHolder(LayoutInflater.from(context).inflate(R.layout.item,parent,false))
        viewHolder.enterDetails.setOnClickListener{
            listener.onClick(viewHolder.enterDetails)
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val curLabel = labels[position]
        holder.label.text  = curLabel.title

    }

    override fun getItemCount(): Int {
        return labels.size
    }
}
interface inf{
    fun onClick(view:EditText)
}