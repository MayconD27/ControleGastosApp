package com.example.controledegastos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.controledegastos.R

class ControleCustoAdapter : RecyclerView.Adapter<ControleCustoAdapter.ControleViewHolder>(){
    class ControleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ControleViewHolder {
        val view  = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_list, parent, false)
        return ControleViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ControleViewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        return 1
    }
}