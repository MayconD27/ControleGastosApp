package com.example.controledegastos.adapter

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controledegastos.R
import com.example.controledegastos.data.ItemCusto
import com.example.controledegastos.model.ControleMolde

class ControleCustoAdapter(val listCusto: ArrayList<ItemCusto>) : RecyclerView.Adapter<ControleCustoAdapter.ControleViewHolder>(){
    class ControleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val valorItem : TextView = itemView.findViewById(R.id.valorItem)
        val categoria : TextView = itemView.findViewById(R.id.categoriaItem)
        val dataItem : TextView = itemView.findViewById(R.id.dataItem)
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

    }

    override fun getItemCount(): Int {
        return listCusto.size
    }

    fun conversorMoeda( valor: Float) : String{
        val valorFormatado = "R$ %.2f".format(valor).replace('.', ',')
        return valorFormatado
    }

}