package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.models.Question

class OptionAdapter(val context: Context,val question: Question):
    RecyclerView.Adapter<OptionAdapter.optionViewHolder>(){

    private var options: List<String> = listOf(question.option1,question.option2,question.option3,question.option4)




    inner class optionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var optionView = itemView.findViewById<TextView>(R.id.quiz_option)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): optionViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.option_item,parent,false)
        return optionViewHolder(view)
    }

    override fun onBindViewHolder(holder: optionViewHolder, position: Int) {
        holder.optionView.text = options[position]
        holder.itemView.setOnClickListener{
            question.userAnswer = options[position]
            notifyDataSetChanged()
        }
        if(question.userAnswer ==options[position]){
            holder.itemView.setBackgroundResource(R.drawable.option_item_selected_bg)
        }
        else{
            holder.itemView.setBackgroundResource(R.drawable.option_item_bg)
        }
    }

    override fun getItemCount(): Int {
        return options.size
    }
}