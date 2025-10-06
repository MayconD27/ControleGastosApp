package com.example.controledegastos.views

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.controledegastos.R
import com.example.controledegastos.data.ItemCusto
import com.example.controledegastos.databinding.ActivityMain3Binding
import com.example.controledegastos.model.ControleMolde
import java.text.SimpleDateFormat
import java.util.Locale

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
        val categorias = listOf("Alimentação", "Transporte", "Educação", "Lazer", "Outros")
        // Cria o adapter de categorias
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.inputCategoriaUpdate.adapter = spinnerAdapter

        if (item != null) {
            binding.inputValorUpdate.setText("${item.valor}")  // aqui já funciona
            binding.inputDateUpdate.setText("${formataData(item.data)}") /// valores das datas

            when(item.categoria) {
                "Alimentação" -> binding.inputCategoriaUpdate.setSelection(0)
                "Transporte" -> binding.inputCategoriaUpdate.setSelection(1)
                "Educação" -> binding.inputCategoriaUpdate.setSelection(2)
                "Lazer" -> binding.inputCategoriaUpdate.setSelection(3)
                "Outros" -> binding.inputCategoriaUpdate.setSelection(4)
            }
        }
        //INput de data
        binding.inputDateUpdate.setOnClickListener {
            //inicia a biblioteca de dadta
            val calendar = Calendar.getInstance()
            //pega o ano
            val year = calendar.get(Calendar.YEAR)
            //pega o mes
            val month = calendar.get(Calendar.MONTH)
            //Pega o dia do mes
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            //funcao para uso da data
            val dataPicker = DatePickerDialog(
                this,
                {
                        _, selectedYear, selectedMonth, selectedDay ->
                    //colocando no input o valor
                    binding.inputDateUpdate.setText("${selectedDay}/${selectedMonth}/${selectedYear}")

                },year,month, day

            )
            dataPicker.show()
        }

        binding.buttonUpdate.setOnClickListener {
            val valorInput = binding.inputValorUpdate.text.toString().toFloat()
            val categoriaValue = binding.inputCategoriaUpdate.selectedItem.toString()
            val dataValue = formataDataBD(binding.inputDateUpdate.text.toString())

            val res = db.updateItem(id, categoriaValue, dataValue, valorInput )
            if(res > 0){
                finish()
            }
            else {
                Toast.makeText(applicationContext, "Erro ao atualizar item ${id}", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonDelete.setOnClickListener {
            var res = db.deleteItem(id)
            if(res > 0){
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Erro ao deletar item ${id}", Toast.LENGTH_SHORT).show()
            }
        }


    }
    fun formataData(dataBD:String) :String{
        // Converte para o formato do banco (yyyy-MM-dd)
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

        val dateFormatted = try {
            val date = inputFormat.parse(dataBD)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "" // ou pode exibir erro
        }

        return dateFormatted
    }
    fun formataDataBD(dataBr:String) :String{
        // Converte para o formato do banco (yyyy-MM-dd)
        val inputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val dateFormatted = try {
            val date = inputFormat.parse(dataBr)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            "" // ou pode exibir erro
        }

        return dateFormatted
    }
}