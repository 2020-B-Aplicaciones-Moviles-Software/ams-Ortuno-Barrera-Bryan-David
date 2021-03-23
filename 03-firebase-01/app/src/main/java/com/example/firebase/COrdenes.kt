package com.example.firebase

import android.app.DownloadManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.firebase.dto.FirestoreRestauranteDto
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import java.util.*

class COrdenes : AppCompatActivity() {
    val arregloRestaurantes = arrayListOf<FirestoreRestauranteDto>()
    var adaptadorRestaurantes: ArrayAdapter<FirestoreRestauranteDto>? = null
    var restauranteSeleccionado: FirestoreRestauranteDto? = null

    val arregloTiposComida = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c_ordenes)

        if(adaptadorRestaurantes == null){
            adaptadorRestaurantes = ArrayAdapter(this,
                android.R.layout.simple_spinner_item,
                arregloRestaurantes
            )
            adaptadorRestaurantes?.
                setDropDownViewResource(
                        android.R.layout.simple_spinner_dropdown_item
                )
            cargarRestaurantes()
        }

        val botonAnadirTipoComida = findViewById<Button>(R.id.btn_anadir_tipo_comida)
        botonAnadirTipoComida
            .setOnClickListener{
                agregarTipoComida()
            }
        val botonCrearOrden = findViewById<Button>(R.id.btn_crear_orden)
        botonCrearOrden
            .setOnClickListener{
                crearOrden()
            }

        //crearDatosParabusquedaPorGrupo()
        //crearLandMarks()
        //buscarCities()
        //eliminacion()
        eliminarDocumentosMedianteConsulta()

    }

    fun cargarRestaurantes(){

        val spinnerRestaurantes = findViewById<Spinner>(R.id.sp_restaurantes)
        spinnerRestaurantes.adapter = adaptadorRestaurantes

        spinnerRestaurantes
            .onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Log.i("firebase-firestore", "No selecciono nada")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                restauranteSeleccionado = arregloRestaurantes[position]
            }
        }
        val db = Firebase.firestore
        val referencia = db.collection("restaurante")
        referencia.get()
            .addOnSuccessListener {
                for (restaurante in it) {
                    val restauranteCasteado = restaurante.toObject(
                        FirestoreRestauranteDto::class.java
                    )
                    restauranteCasteado.uid = restaurante.id
                    arregloRestaurantes.add(restauranteCasteado)
                }
                adaptadorRestaurantes?.notifyDataSetChanged()
            }
            .addOnFailureListener{
                Log.i("firebase-firestore","Error ${it}")
            }
    }

    fun agregarTipoComida() {
        val etTipoComida = findViewById<EditText>(R.id.et_tipo_comida)
        val textoTipoComida = etTipoComida.text.toString()

        arregloTiposComida.add(textoTipoComida)

        val textViewTipoComida = findViewById<TextView>(R.id.tv_tipos_comida)

        val textoAnterior = textViewTipoComida.text.toString()

        textViewTipoComida.setText("${textoAnterior}, ${textoTipoComida}")
        etTipoComida.setText("")
    }

    fun crearOrden(){
            if(restauranteSeleccionado != null && FirebaseAuth.getInstance().currentUser!=null){

                val restaurante = restauranteSeleccionado
                val usuario = BAuthUsuario.usuario
                val editTextReview = findViewById<EditText>(R.id.et_review)

                val nuevaOrden = hashMapOf<String, Any>(
                    "restaurante" to restaurante.toString(),
                    "usuario" to usuario.toString(),
                    "review" to editTextReview.text.toString().toInt(),
                    "tiposComida" to arregloTiposComida,
                    "fechaCreacion" to Timestamp(Date())
                )

                val db = Firebase.firestore
                val referencia = db.collection("orden")
                    .document()

                referencia
                    .set(nuevaOrden)
                    .addOnSuccessListener {  }
                    .addOnFailureListener {  }
            }
        }

    fun buscarOrdenes(){

        val db = Firebase.firestore
        val referencia = db.collection("orden")

        //Buscar por 1 campo
        referencia
                .whereEqualTo("review",3)
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }

        //Buscar por dos campo ==
        referencia
                .whereEqualTo("review",3)
                .whereEqualTo("restaurante.nombre", "fridays")
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }

        //Buscar por dos o mas elementos campo == array_contains
        referencia
                .whereEqualTo("restaurante.nombre","Las papas de la maria")
                .whereArrayContains("tiposComida", "ecuatoriana")
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }
        //Buscar por dos o mas elementos campo == >=
        referencia
                .whereEqualTo("restaurante.nombre","Fridays")
                .whereGreaterThanOrEqualTo("review", 3)
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }

        referencia
                .whereEqualTo("restaurante.nombre","fridays")
                .whereArrayContains("review", 3)
                .orderBy("review", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }

        //Buscar por dos o mas elementos campo == array-contains-any

        referencia
                .whereEqualTo("restaurante.nombre","Fridays")
                .whereArrayContainsAny("tiposComida", arrayListOf("ecuatoriana","japonesa"))
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }

        referencia
                .whereIn("restaurante.nombre", arrayListOf("Fridays","papas de la maria","pepito"))
                .whereGreaterThanOrEqualTo("review", 1)
                .get()
                .addOnSuccessListener {
                    for (orden in it){
                        Log.i("firebase-consultas","${orden.id} ${orden.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","Error")
                }




    }

    fun buscarCities(){

        val db = Firebase.firestore
//        val referencia = db.collection("cities").document("BJ")
//
//        referencia.collection("landmarks").whereEqualTo("type", "park").get()
//                .addOnSuccessListener {
//                    for (landmark in it){
//                        Log.i("firebase-consultas","${landmark.id} ${landmark.data}")
//                    }
//                }
//                .addOnFailureListener {
//                    Log.i("firebase-firestore","Error")
//                }

        val referencia = db.collectionGroup("landmarks")

        referencia
                .whereEqualTo("type", "park")
                .get()
                .addOnSuccessListener {
                    for (landmark in it){
                        Log.i("firebase-consultas","${landmark.id} ${landmark.data}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore","${it}")
                }

    }

    fun crearDatosParabusquedaPorGrupo(){

        val db = Firebase.firestore
        val cities = db.collection("cities")

        val data1 = hashMapOf("name" to "San Francisco","state" to "CA","country" to "USA","capital" to false,"population" to 860000,"regions" to listOf("west_coast", "norcal"))
        cities.document("SF").set(data1)
        val data2 = hashMapOf("name" to "Los Angeles","state" to "CA","country" to "USA","capital" to false,"population" to 3900000,"regions" to listOf("west_coast", "socal"))
        cities.document("LA").set(data2)
        val data3 = hashMapOf("name" to "Washington D.C.","state" to null,"country" to "USA","capital" to true,"population" to 680000,"regions" to listOf("east_coast"))
        cities.document("DC").set(data3)
        val data4 = hashMapOf("name" to "Tokyo","state" to null,"country" to "Japan","capital" to true,"population" to 9000000,"regions" to listOf("kanto", "honshu"))
        cities.document("TOK").set(data4)
        val data5 = hashMapOf("name" to "Beijing","state" to null,"country" to "China","capital" to true,"population" to 21500000,"regions" to listOf("jingjinji", "hebei"))
        cities.document("BJ").set(data5)
    }

    fun crearLandMarks(){

        val db = Firebase.firestore
        val citiesRef = db.collection("cities")

//        val ggbData = mapOf(
//                "name" to "Golden Gate Bridge",
//                "type" to "bridge"
//        )
//        citiesRef.document("SF").collection("landmarks").add(ggbData)
//
//        val lohData = mapOf(
//                "name" to "Legion of Honor",
//                "type" to "museum"
//        )
//        citiesRef.document("SF").collection("landmarks").add(lohData)
//
//        val gpData = mapOf(
//                "name" to "Griffth Park",
//                "type" to "park"
//        )
//        citiesRef.document("LA").collection("landmarks").add(gpData)
//
//        val tgData = mapOf(
//                "name" to "The Getty",
//                "type" to "museum"
//        )
//        citiesRef.document("LA").collection("landmarks").add(tgData)
//
//        val lmData = mapOf(
//                "name" to "Lincoln Memorial",
//                "type" to "memorial"
//        )
//        citiesRef.document("DC").collection("landmarks").add(lmData)
//
//        val nasaData = mapOf(
//                "name" to "National Air and Space Museum",
//                "type" to "museum"
//        )
//        citiesRef.document("DC").collection("landmarks").add(nasaData)
//
//        val upData = mapOf(
//                "name" to "Ueno Park",
//                "type" to "park"
//        )
//        citiesRef.document("TOK").collection("landmarks").add(upData)
//
//        val nmData = mapOf(
//                "name" to "National Musuem of Nature and Science",
//                "type" to "museum"
//        )
//        citiesRef.document("TOK").collection("landmarks").add(nmData)

        val jpData = mapOf(
                "name" to "Jingshan Park",
                "type" to "park"
        )
        citiesRef.document("BJ").collection("landmarks").add(jpData)

        val baoData = mapOf(
                "name" to "Beijing Ancient Observatory",
                "type" to "musuem"
        )
        citiesRef.document("BJ").collection("landmarks").add(baoData)
    }

    fun eliminacion(){
        val db = Firebase.firestore
        val docRef = db
                .collection("cities")
                .document("BJ")
                .collection("landmarks")
                .document("Xq9TXjmLLCiJGC2tjcpG")
        val eliminarCampo = hashMapOf<String,Any>(
                "name" to FieldValue.delete()
        )
//        docRef
//                .update(eliminarCampo)
//                .addOnSuccessListener {
//                    Log.i("firebase-delete","${it}")
//                }
//                .addOnFailureListener{
//                    Log.i("firebase-delete","Error al borrar campo")
//                }
        docRef
                .delete()
                .addOnSuccessListener {
                    Log.i("firebase-delete","${it}")
                }
                .addOnFailureListener{
                    Log.i("firebase-delete","Error al borrar campo")
                }
    }

    //Eliminar docuemtos mediante consulta
    // ej. tipo museo
    fun eliminarDocumentosMedianteConsulta(){
        //Tipo musuem

        val db = Firebase.firestore
        val referencia = db.collectionGroup("landmarks")

        referencia
                .whereEqualTo("type", "museum")
                .get()
                .addOnSuccessListener {
                    for (landmark in it){
                        landmark.reference.delete()
                        Log.i("firebase-delete","${it}")
                    }
                }
                .addOnFailureListener {
                    Log.i("firebase-delete","${it}")
                }

    }


}
