package com.example.final_project

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RowNumberBinding

class AdapterNumber: RecyclerView.Adapter<AdapterNumber.HolderNumber>{
    private val context: Context
    private val numberArrayList: ArrayList<ModelNumber>



    private lateinit var binding: RowNumberBinding

    constructor(context: Context, numberArrayList: ArrayList<ModelNumber>) {
        this.context = context
        this.numberArrayList = numberArrayList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderNumber {
        binding = RowNumberBinding.inflate(LayoutInflater.from(context), parent, false)
        return HolderNumber(binding.root)
    }

    override fun onBindViewHolder(holder: HolderNumber, position: Int) {
        val model = numberArrayList[position]
        val id = model.id
        val number = model.number
        val uid = model.uid
        val timestamp = model.timestamp

        holder.numberTv.text = number
    }

    override fun getItemCount(): Int {
        return numberArrayList.size
    }

    inner class HolderNumber(itemView: View): RecyclerView.ViewHolder(itemView){
        var numberTv:TextView = binding.numberTv
    }
}