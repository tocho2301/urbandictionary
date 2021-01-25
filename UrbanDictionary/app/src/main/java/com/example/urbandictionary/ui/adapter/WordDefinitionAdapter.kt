package com.example.urbandictionary.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.urbandictionary.databinding.ItemDefinitionBinding
import com.example.urbandictionary.entity.WordDefinition

class WordDefinitionAdapter (var list: ArrayList<WordDefinition>): RecyclerView.Adapter<WordDefinitionAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ItemDefinitionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(arrayList: ArrayList<WordDefinition>){
        this.list = arrayList
        notifyDataSetChanged()
    }

    fun orderByThumbsDown() {
        list.sortByDescending { it.thumbsDown }
        notifyDataSetChanged()
    }

    fun orderByThumbsUp(){
        list.sortByDescending { it.thumbsUp }
        notifyDataSetChanged()
    }

    class ViewHolder(private val itemBinding: ItemDefinitionBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(wordDefinition: WordDefinition){
            itemBinding.tvDefinition.text = wordDefinition.definition
            itemBinding.tvExample.text = wordDefinition.example
            itemBinding.tvThumbDown.text = wordDefinition.thumbsDown.toString()
            itemBinding.tvThumbUp.text = wordDefinition.thumbsUp.toString()
        }
    }
}