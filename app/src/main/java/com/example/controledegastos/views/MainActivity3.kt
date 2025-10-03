package com.example.controledegastos.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.controledegastos.R
import com.example.controledegastos.data.ItemCusto
import com.example.controledegastos.databinding.ActivityMain3Binding
import com.example.controledegastos.model.ControleMolde

class MainActivity3 : AppCompatActivity() {
    private lateinit var binding: ActivityMain3Binding
    private val db = ControleMolde(this)
    private lateinit var data: ArrayList<ItemCusto>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.isBack.setOnClickListener {
            finish()
        }
        val id = intent.getIntExtra("id",-1)
        val items = db.sellectById(id)   // isso retorna ArrayList<ItemCusto>
        val item = items.firstOrNull()   // pega o primeiro, ou null se não existir

        if (item != null) {
            binding.inputValorUpdate.setText("${item.valor}")  // aqui já funciona
        } else {
            binding.inputValorUpdate.setText("Não encontrado")
        }

    }
}