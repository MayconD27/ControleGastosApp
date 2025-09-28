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
import com.example.controledegastos.databinding.ActivityMain2Binding
import com.example.controledegastos.model.ControleMolde
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.compareTo

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private val db = ControleMolde(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        val categorias = listOf("Alimentação", "Transporte", "Educação", "Lazer", "Outros")
        // Cria o adapter de categorias
        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categorias
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Atribui o adapter usando o binding
        binding.spinnerCategoria.adapter = spinnerAdapter

        //INput de data
        binding.dataInput.setOnClickListener {
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
                    binding.dataInput.setText("${selectedDay}/${selectedMonth}/${selectedYear}")

                },year,month, day

            )
            dataPicker.show()
        }

        //Botâo de voltar
        binding.isBack.setOnClickListener {
            finish()
        }

        binding.btnEntrada.setOnClickListener {
            val valor = binding.inputValor.text.toString().toFloat()
            val category = binding.spinnerCategoria.selectedItem.toString()
            var data = binding.dataInput.text.toString()

            data = formataData(data)
            val result = db.insertItem(valor, category, data)
            if(result > 0){
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Erro ao inserir os dados", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSaida.setOnClickListener {
            val valor = binding.inputValor.text.toString().toFloat()
            val category = binding.spinnerCategoria.selectedItem.toString()
            var data = binding.dataInput.text.toString()

            data = formataData(data)
            val result = db.insertItem(valor*(-1), category, data)
            if(result > 0){
                finish()
            }
            else{
                Toast.makeText(applicationContext, "Erro ao inserir os dados", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun formataData(dataBr:String) :String{
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