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
import com.example.examen1b.data.Marca
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val db by lazy { AppDataBase(this) }
    lateinit var marcaAdapter: MarcaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupListener()
        setupRecyclerView()

    }

    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            marcaAdapter.setData(db.marcaDAO().readData())
            withContext(Dispatchers.Main) {
                marcaAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        cargarDatos()
    }


    override fun onStart(){
        super.onStart()
        CoroutineScope(Dispatchers.IO).launch {
            val marcas = db.marcaDAO().readData()
            //db.marcaDAO().nukeTable()
            Log.d("MainActivity","dbResponde: ${marcas}")
            withContext(Dispatchers.Main){
                marcaAdapter.setData(marcas)
            }
        }

    }

    fun setupListener(){
        agregarCelularButton.setOnClickListener{
            startActivity(Intent(this,CrearMarca::class.java))
        }
    }

    fun setupRecyclerView(){
        marcaAdapter = MarcaAdapter(arrayListOf(), object : MarcaAdapter.OnAdapterListener{
            override fun onSimpleClick(marca: Marca){
                Log.d("MainActivity","itemClick: Hizo click")
                mostrarDetalles(marca)
            }
            override fun onClick(marca: Marca, view: View):Boolean{
                val popup = PopupMenu(applicationContext,view)
                popup.inflate(R.menu.menu)
                popup.setOnMenuItemClickListener { item ->
                    when(item.itemId){
                        R.id.mi_listar -> {
                            startActivity(
                                Intent(applicationContext,ListarCelulares::class.java).putExtra("intent_id",marca.idMarca)

                            )
                            true
                        }
                        R.id.mi_editar -> {
                            startActivity(
                                Intent(applicationContext,EditarMarca::class.java).putExtra("intent_id",marca.idMarca)
                            )
                            true
                        }
                        R.id.mi_eliminar -> {
                            deleteAlert(marca)
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
            adapter = marcaAdapter
        }
    }

    private fun deleteAlert(marca: Marca){
        val dialog = AlertDialog.Builder(this)
        dialog.apply {
            setTitle("Confirmar Eliminar")
            setMessage("Seguro que quiere borrar: ${marca.nombre}?")
            setNegativeButton("Cancelar") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            setPositiveButton("Eliminar") { dialogInterface, i ->
                CoroutineScope(Dispatchers.IO).launch {
                    db.marcaDAO().deleteMarca(marca)
                    dialogInterface.dismiss()
                    cargarDatos()
                }
            }
        }

        dialog.show()
    }

    private fun mostrarDetalles(marca: Marca){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Detalles")

        val marcas = arrayOf(
                "Marca: ${marca.nombre}",
                "Fecha Fundación: ${marca.fechaFundacion}",
                "Empresa de la marca: ${marca.empresa}",
                "País de la marca: ${marca.pais}")
        builder.setItems(marcas) { dialog, which ->

        }
        val dialog = builder.create()
        dialog.show()
    }



}