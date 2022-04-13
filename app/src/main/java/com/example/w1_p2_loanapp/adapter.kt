package com.example.w1_p2_loanapp

import android.content.Context
import android.content.SharedPreferences
import android.text.Editable
import android.text.InputType
import android.view.LayoutInflater
import android.view.View

import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class adapter(private val context: Context,private val labels: ArrayList<field>,private val listener:inf) :RecyclerView.Adapter<adapter.viewHolder>(){

    inner class viewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val label = itemView.findViewById<TextView>(R.id.label)
        val enterDetails  = itemView.findViewById<EditText>(R.id.enterDetails)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val view:View = LayoutInflater.from(context).inflate(R.layout.item,parent,false)
        val viewHolder= viewHolder(view)
        viewHolder.enterDetails.setOnClickListener{
            listener.onClick(viewHolder.enterDetails,viewHolder.adapterPosition,
                labels[viewHolder.adapterPosition].title)
        }


        return viewHolder
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val curLabel = labels[position]
        holder.label.text  = curLabel.title
        if(curLabel.type == "text")
            holder.enterDetails.inputType = InputType.TYPE_CLASS_TEXT
        else if(curLabel.type == "number")
            holder.enterDetails.inputType = InputType.TYPE_CLASS_NUMBER
        else if(curLabel.type == "phone")
            holder.enterDetails.inputType  = InputType.TYPE_CLASS_PHONE
        val sharedPreferences:SharedPreferences = holder.itemView.context.getSharedPreferences("sharedPrefs",Context.MODE_PRIVATE)
        val savedData: String? = sharedPreferences.getString(labels[position].title,"")
        holder.enterDetails.text= Editable.Factory.getInstance().newEditable(savedData)

    }

    override fun getItemCount(): Int {
        return labels.size
    }
}
interface inf{
    fun onClick(view:EditText, position: Int,title:String)
}