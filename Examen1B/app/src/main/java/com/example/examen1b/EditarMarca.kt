package com.example.examen1b

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.examen1b.data.AppDataBase
import com.example.examen1b.data.Marca
import kotlinx.android.synthetic.main.activity_editar_marca.*
import kotlinx.android.synthetic.main.activity_editar_marca.editEmpresaMarca
import kotlinx.android.synthetic.main.activity_editar_marca.editFechaMarca
import kotlinx.android.synthetic.main.activity_editar_marca.editNombreMarca
import kotlinx.android.synthetic.main.activity_editar_marca.editPaisMarca
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

class EditarMarca : AppCompatActivity() {

    private val db by lazy { AppDataBase(this) }
    private var marcaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_marca)
        getMarca()
        setupLstener()
    }

    private fun setupLstener(){
        editarMarcaButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                db.marcaDAO().updateMarca(
                    Marca(
                        editNombreMarca.text.toString(),
                        SimpleDateFormat("dd/MM/yyyy").parse(editFechaMarca.text.toString()),
                        editEmpresaMarca.text.toString(),
                        editPaisMarca.text.toString(),
                        marcaId))
                finish()
            }
        }
    }

    private fun getMarca(){
        marcaId = intent.getIntExtra("intent_id", 0)
        CoroutineScope(Dispatchers.IO).launch {
            val marca = db.marcaDAO().getMarca(marcaId).get(0)
            editNombreMarca.setText( marca.nombre )
            editFechaMarca.setText(SimpleDateFormat("dd/MM/yyyy").format(marca.fechaFundacion))
            editEmpresaMarca.setText(marca.empresa)
            editPaisMarca.setText(marca.pais)
        }
    }

}