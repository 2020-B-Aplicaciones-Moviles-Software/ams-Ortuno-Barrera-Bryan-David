package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebase.dto.FirestoreOrdenDto
import com.example.firebase.dto.FirestoreRestauranteDto
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_c_ordenes_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class COrdenesList : AppCompatActivity() {
    var arregloOrdenes = arrayListOf<FirestoreOrdenDto>()
    lateinit var ordenAdapter: OrdenAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes_list)
        val botonAgregarOrdenes=findViewById<Button>(R.id.agregarORdenButton)
        buscarOrdenes()
        botonAgregarOrdenes.setOnClickListener{
            buscarOrdenes()
        }
        setupRecyclerView()
    }


    fun buscarOrdenes(){
        val db = Firebase.firestore
        val referencia = db.collection("orden")

        referencia
            .limit(2)
            .get()
            .addOnSuccessListener {
                    for (orden in it) {

                        val ordenCasteada = FirestoreOrdenDto(
                            orden.id,
                            orden.data.get("restaurante").toString(),
                            orden.data.get("review").toString(),
                            orden.data.get("tiposComida").toString()
                        )
                        arregloOrdenes.add(ordenCasteada)
                        Log.i("firebase-consultas", "${arregloOrdenes}")
                    }
                cargarDatos()

                val ultimoRegistro : QueryDocumentSnapshot = it.last()
                //siguientes dos ordenes
                referencia
                    .limit(2)
                    .startAfter(ultimoRegistro)
                    .get().addOnSuccessListener {
                        for (orden in it){
                            Log.i("firebase-consultas","${orden.id} ${orden.data}")
                        }
                    }
                    .addOnFailureListener{
                        Log.i("firebase-firestore","Error")
                    }
            }
            .addOnFailureListener{
                Log.i("firebase-firestore","Error")
            }

    }

    fun setupRecyclerView(){
        ordenAdapter = OrdenAdapter(arrayListOf())
        recyclerViewOrdenes.apply {
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = ordenAdapter
        }
    }

    private fun cargarDatos(){
        CoroutineScope(Dispatchers.IO).launch {
            ordenAdapter?.setData(arregloOrdenes)
            withContext(Dispatchers.Main) {
                ordenAdapter?.notifyDataSetChanged()
            }
        }
    }

}