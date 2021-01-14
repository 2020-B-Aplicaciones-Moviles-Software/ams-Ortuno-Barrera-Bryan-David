package com.example.examen1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.TypeConverters
import com.example.examen1b.data.AppDataBase
import com.example.examen1b.data.Marca
import kotlinx.android.synthetic.main.activity_crear_marca.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


@TypeConverters(Converters::class)
class CrearMarca : AppCompatActivity() {

    private val db by lazy { AppDataBase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_marca)
        setupListener()

    }

    fun setupListener() {
        crearMarcaButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.marcaDAO().addMarca(Marca(
                        editNombreMarca.text.toString(),
                        SimpleDateFormat("dd/MM/yyyy").parse(editFechaMarca.text.toString()),
                        editEmpresaMarca.text.toString(),
                        editPaisMarca.text.toString()))
            }
            finish()
        }
    }

}