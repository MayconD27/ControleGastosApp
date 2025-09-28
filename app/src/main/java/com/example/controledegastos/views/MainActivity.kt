package com.example.controledegastos.views

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
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

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        data = db.selectAllItems()

        adapter = ControleCustoAdapter(data)

        //Para carregar o adapter
        binding.recyclerView.adapter = adapter

        //colocando o total dos valores
        val valorTotal = db.sumItemsValue()
        binding.totalValue.setText("${conversorMoeda(valorTotal)}")
        binding.addItem.setOnClickListener {
            val i = Intent(this, MainActivity2::class.java)
            startActivity(i)
        }
    }
    fun conversorMoeda( valor: Float) : String{
        val valorFormatado = "R$ %.2f".format(valor).replace('.', ',')
        return valorFormatado
    }
}