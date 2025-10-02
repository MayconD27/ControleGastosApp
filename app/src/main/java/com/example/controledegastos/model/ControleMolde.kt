package com.example.controledegastos.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.controledegastos.data.ItemCusto

class ControleMolde(context: Context): SQLiteOpenHelper(context, "database.db", null,1){
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE control (id INTEGER PRIMARY KEY AUTOINCREMENT,valor DOUBLE,categoria VARCHAR, data DATE)")
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE control")
        onCreate(db)
    }

    fun selectAllItems() : ArrayList<ItemCusto>{
        val db = this.readableDatabase

        val c = db.rawQuery("SELECT * FROM control",null)
        val controlList : ArrayList<ItemCusto> = ArrayList()
        if(c.count > 0){
            c.moveToFirst()
            do {
                //Faz os bindParam das colunas
                val idIndex = c.getColumnIndex("id")
                val valorIndex = c.getColumnIndex("valor")
                val categoriaIndex = c.getColumnIndex("categoria")
                val dateIndex = c.getColumnIndex("data")

                //Pega os valores
                val id = c.getInt(idIndex)
                val valor = c.getFloat(valorIndex)
                val categoria = c.getString(categoriaIndex)
                val data = c.getString(dateIndex)


                //Incrementar a task
                controlList.add(ItemCusto(id,valor, categoria, data))
            }while (c.moveToNext())
        }
        return controlList
    }
    fun sellectById(id: Int) : ArrayList<ItemCusto>{
        val db = this.readableDatabase
        val c = db.rawQuery("SELECT * FROM control WHERE id=?", arrayOf(id.toString()))
        var controlList : ArrayList<ItemCusto> = ArrayList()
        if (c.moveToFirst()) {
            do {
                val idIndex = c.getColumnIndex("id")
                val valorIndex = c.getColumnIndex("id")
                val categoriaIndex = c.getColumnIndex("categoria")
                val dateIndex = c.getColumnIndex("data")

                val id = c.getInt(idIndex)
                val valor = c.getFloat(valorIndex)
                val categoria = c.getString(categoriaIndex)
                val data = c.getString(dateIndex)

                controlList.add(ItemCusto(id,valor,categoria,data))
                // ... pega as outras colunas
            } while (c.moveToNext())
        }
        c.close()
        return controlList
    }


    fun sumItemsValue() : Float{
        val db = this.readableDatabase

        val c = db.rawQuery("SELECT SUM(valor) AS total FROM control",null)
        var total = 0.00f
        if(c.count > 0){
            c.moveToFirst()
            do {
                //Faz os bindParam das colunas
                val totalIndex = c.getColumnIndex("total")

                //Pega os valores
                total = c.getFloat(totalIndex)
            }while (c.moveToNext())
        }
        return total
    }
    fun insertItem(valor:Float, categoria:String, data:String) :Long{
        val db = this.writableDatabase
        val contentValue  = ContentValues()
        contentValue.put("valor", valor)
        contentValue.put("categoria", categoria)
        contentValue.put("data", data)

        //escrevendo o insert
        val res = db.insert("control", null, contentValue)
        return  res

    }
    fun resetDatabase() {
        val db = this.writableDatabase
        db.execSQL("DROP TABLE IF EXISTS control")
        onCreate(db)
    }




}
