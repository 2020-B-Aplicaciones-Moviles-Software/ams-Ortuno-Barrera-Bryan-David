package com.example.examen1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.room.TypeConverters
import com.example.examen1b.data.AppDataBase
import com.example.examen1b.data.Celular
import kotlinx.android.synthetic.main.activity_crear_celular.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@TypeConverters(Converters::class)
class CrearCelular : AppCompatActivity() {

    private val db by lazy { AppDataBase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_celular)
        setupListener()
    }
    fun setupListener() {

        crearCelularButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val marcaId = intent.getIntExtra("intent_id", 0)
                db.celularDAO().addCelular(
                    Celular(
                        editNombreCelular.text.toString(),
                        SimpleDateFormat("dd/MM/yyyy").parse(editFechaCelular.text.toString()),
                        editPrecioCelular.text.toString().toDouble(),
                        checkBoxLectorHuella.isChecked,
                        editPixelesCelular.text.toString().toInt(),
                        marcaId)
                )

            }
            finish()
        }
    }


}