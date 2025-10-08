package com.example.controledegastos.views

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.controledegastos.R
import com.example.controledegastos.adapter.ControleCustoAdapter
import com.example.controledegastos.data.ItemCusto
import com.example.controledegastos.databinding.ActivityMainBinding
import com.example.controledegastos.model.ControleMolde

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ControleCustoAdapter

    //Aqui pegamos a lista padrão
    private lateinit var data: ArrayList<ItemCusto>
    private val db = ControleMolde(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //Meu resete de banco de dados para caso encontrar uma falha
        //db.resetDatabase()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        data = db.selectAllItems()

        adapter = ControleCustoAdapter(data)

        //Para carregar o adapter
        binding.recyclerView.adapter = adapter

        //colocando o total dos valores
        val valorTotal = db.sumItemsValue()
        if(valorTotal<0){
            binding.totalValue.setTextColor(Color.RED)
        }
        else{
            binding.totalValue.setTextColor(Color.parseColor("#333333"))
        }
        binding.totalValue.setText("${conversorMoeda(valorTotal)}")
        binding.addItem.setOnClickListener {
            val i = Intent(this, MainActivity2::class.java)
            startActivity(i)
        }
        binding.cleanList.setOnClickListener {


            AlertDialog.Builder(this)
                .setTitle("Confirmação")
                .setMessage("Tem certeza que deseja continuar?")
                .setPositiveButton("Confirmar") { dialog, _ ->
                    // Aqui é o "onConfirm"
                    db.resetDatabase()
                    adapter = ControleCustoAdapter(data)
                    binding.recyclerView.adapter = adapter

                    // Atualiza o valor total
                    val valorTotal = db.sumItemsValue()
                    if(valorTotal<0){
                        binding.totalValue.setTextColor(Color.RED)
                    }
                    else{
                        binding.totalValue.setTextColor(Color.parseColor("#333333"))
                    }
                    binding.totalValue.text = conversorMoeda(valorTotal)
                    dialog.dismiss()
                }
                .setNegativeButton("Cancelar") { dialog, _ ->
                    // Cancelar
                    Toast.makeText(this, "Cancelado!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
    override fun onResume() {
        super.onResume()

        // Recarrega a lista do banco de dados direto aqui
        val data = db.selectAllItems()
        adapter = ControleCustoAdapter(data)
        binding.recyclerView.adapter = adapter

        // Atualiza o valor total
        val valorTotal = db.sumItemsValue()
        if(valorTotal<0){
            binding.totalValue.setTextColor(Color.RED)
        }
        else{
            binding.totalValue.setTextColor(Color.parseColor("#333333"))
        }
        binding.totalValue.text = conversorMoeda(valorTotal)
    }

    fun conversorMoeda( valor: Float) : String{
        val valorFormatado = "R$ %.2f".format(valor).replace('.', ',')
        return valorFormatado
    }
}