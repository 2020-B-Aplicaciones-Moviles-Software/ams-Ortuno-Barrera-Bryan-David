package com.example.examen1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen1b.data.AppDataBase
import com.example.examen1b.data.Celular
import com.example.examen1b.data.Marca
import kotlinx.android.synthetic.main.activity_crear_celular.*
import kotlinx.android.synthetic.main.activity_crear_celular.checkBoxLectorHuella
import kotlinx.android.synthetic.main.activity_crear_celular.editFechaCelular
import kotlinx.android.synthetic.main.activity_crear_celular.editNombreCelular
import kotlinx.android.synthetic.main.activity_crear_celular.editPixelesCelular
import kotlinx.android.synthetic.main.activity_crear_celular.editPrecioCelular
import kotlinx.android.synthetic.main.activity_editar_celular.*
import kotlinx.android.synthetic.main.activity_editar_marca.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class EditarCelular : AppCompatActivity() {

    private val db by lazy { AppDataBase(this) }
    private var celularId = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_celular)
        getCelular()
        setupLstener()
    }

    private fun setupLstener(){
        editarCelularButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val marcaId = intent.getIntExtra("intent_idM", 0)
                db.celularDAO().updateCelular(
                        Celular(
                                editNombreCelular.text.toString(),
                                SimpleDateFormat("dd/MM/yyyy").parse(editFechaCelular.text.toString()),
                                editPrecioCelular.text.toString().toDouble(),
                                checkBoxLectorHuella.isChecked,
                                editPixelesCelular.text.toString().toInt(),
                                marcaId,
                                celularId))
                finish()
            }
        }
    }

    private fun getCelular(){
        celularId = intent.getIntExtra("intent_idC", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val celular = db.celularDAO().getCelular(celularId).get(0)
            editNombreCelular.setText( celular.nombre )
            editFechaCelular.setText(SimpleDateFormat("dd/MM/yyyy").format(celular.anio))
            editPrecioCelular.setText(celular.precio.toString())
            //checkBoxLectorHuella.isChecked = celular.lectorDeHuellas
            editPixelesCelular.setText(celular.pixelesCamara.toString())
        }
    }
}