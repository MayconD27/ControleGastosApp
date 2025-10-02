package com.example.controledegastos.adapter

import android.graphics.Color
import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
        val image : ImageView = itemView.findViewById(R.id.imageCategoria)
        fun bind(item: ItemCusto) {

        }
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
     val list = listCusto[position]
     holder.valorItem.setText("${conversorMoeda(list.valor)}")
     holder.categoria.setText("${converterCat(list.categoria)}")
     holder.dataItem.setText("${formatDate(list.data)}")
    holder.image.setImageResource(img(list.categoria))

    if (list.valor < 0) {
        holder.valorItem.setTextColor(Color.RED) // Importa android.graphics.Color
    } else {
        holder.valorItem.setTextColor(Color.parseColor("#333333")) // Ou a cor padrão que quiser
    }
    }

    override fun getItemCount(): Int {
        return listCusto.size
    }

    fun conversorMoeda( valor: Float) : String{
        val valorFormatado = "R$ %.2f".format(valor).replace('.', ',')
        return valorFormatado
    }
    //formatar categorias
    fun converterCat(text: String) : String{
        var formated = ""
        if(text == "Alimentação"){
            formated = "Ali."
        }
        else if(text == "Transporte"){
            formated = "Tran."
        }
        else if(text == "Educação"){
            formated = "Edu."
        }
        else if(text == "Lazer"){
            formated = "Laz."
        }
        else if(text == "Outros"){
            formated = "Out."
        }
        return formated
    }
    //formatarData
    fun formatDate(date: String): String {
        val parts = date.split("-")
        if (parts.size == 3) {
            val day = parts[2]
            val month = parts[1]
            return "$day/$month"
        }
        return ""
    }

    fun img(text: String): Int {
        return when(text) {
            "Alimentação" -> R.drawable.food
            "Transporte" -> R.drawable.transp
            "Educação" -> R.drawable.edu
            "Lazer" -> R.drawable.laz
            "Outros" -> R.drawable.out
            else -> R.drawable.out  // valor padrão para casos não previstos
        }
    }




}