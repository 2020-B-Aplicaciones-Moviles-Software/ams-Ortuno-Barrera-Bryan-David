package com.example.examen1b

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examen1b.data.AppDataBase
import com.example.examen1b.data.Celular
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListarCelulares : AppCompatActivity() {

    private val db by lazy { AppDataBase(this) }
    lateinit var celularAdapter: CelularAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar_celulares)
        setupListener()
        setupRecyclerView()
    }

    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            val marcaId = intent.getIntExtra("intent_id", 0)
            val marcaConCelular = db.marcaDAO().getMarcaConCelulares(marcaId)
            var celular:List<Celular> = arrayListOf()
            marcaConCelular.forEach{
                celular = it.celular
            }
            celularAdapter.setData(celular)
            withContext(Dispatchers.Main) {
                celularAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStart(){
        super.onStart()
        cargarDatos()
        CoroutineScope(Dispatchers.IO).launch {
            val marcaId = intent.getIntExtra("intent_id", 0)
            val marcaConCelular = db.marcaDAO().getMarcaConCelulares(marcaId)
            Log.d("ListActivity","dbResponde: ${marcaConCelular}")
            var celular:List<Celular> = arrayListOf()
            marcaConCelular.forEach{
                celular = it.celular
            }
            withContext(Dispatchers.Main){
                celularAdapter.setData(celular)
            }
        }

    }

    fun setupRecyclerView(){
        celularAdapter = CelularAdapter(arrayListOf(), object : CelularAdapter.OnAdapterListener{
            val marcaId = intent.getIntExtra("intent_id", 0)
            override fun onSimpleClick(celular: Celular) {
                mostrarDetalles(celular)
            }
            override fun onClick(celular: Celular, view: View): Boolean {
                val popup = PopupMenu(applicationContext,view)
                popup.inflate(R.menu.menu2)
                popup.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.mi_editar2 -> {
                            startActivity(
                                    Intent(applicationContext,EditarCelular::class.java).putExtra("intent_idC",celular.idCelular).putExtra("intent_idM",marcaId)
                            )
                            true
                        }
                        R.id.mi_eliminar2 -> {
                            deleteAlert(celular)
                            true
                        }
                        else -> false
                    }
                }
                popup.show()
                return@onClick true
            }

        })

        recyclerViewCelular.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = celularAdapter

        }
    }

    fun setupListener(){
        agregarCelularButton.setOnClickListener{
            val marcaId = intent.getIntExtra("intent_id", 0)
            startActivity(Intent(this,CrearCelular::class.java).putExtra("intent_id",marcaId))
        }
    }


    private fun deleteAlert(celular: Celular){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Confirmar Eliminar")
            setMessage("Seguro que quiere borrar: ${celular.nombre}?")
            setNegativeButton("Cancelar") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Eliminar") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.celularDAO().deleteCelular(celular)
                    dialogInterface.dismiss()
                    cargarDatos()
                }
            }
        }

        dialog.show()
    }

    private fun mostrarDetalles(celular: Celular){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detalles")

        val celulares = arrayOf(
                "Nombre celular: ${celular.nombre}",
                "Año celular: ${celular.anio}",
                "Precio celular: ${celular.precio}",
                "Lector de huella: ${celular.lectorDeHuellas}",
                "Pixeles de la cámara: ${celular.pixelesCamara}")
        builder.setItems(celulares) { dialog, which ->
        }
        val dialog = builder.create()
        dialog.show()
    }
}