package com.example.proyectoiib_moviesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.proyectoiib_moviesapp.dto.DatosPago
import com.example.proyectoiib_moviesapp.dto.PeliculasFavoritas
import com.example.proyectoiib_moviesapp.dto.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonIngresar = findViewById<Button>(R.id.btn_continuar)
        botonIngresar
            .setOnClickListener {
                pedidoIngresar()
            }
        val botonCrearCuenta = findViewById<Button>(R.id.btn_crear_cuenta)
        botonCrearCuenta
            .setOnClickListener{
                val intentR = Intent(this,CrearCuenta::class.java)
                startActivity(intentR)
            }

    }

    override fun onStart(){
        super.onStart()
        val instanciaAuth = FirebaseAuth.getInstance()
        if (instanciaAuth.currentUser != null) {
            setearUsuarioFirebase()
        }

    }


    fun setearUsuarioFirebase() {
        val instanciaAuth = FirebaseAuth.getInstance()
        val usuarioLocal = instanciaAuth.currentUser

        if (usuarioLocal != null) {
            if (usuarioLocal.email != null) {

                val db = Firebase.firestore
                val referencia = db.collection("usuario").document(usuarioLocal.email.toString())

                //Buscar por 1 campo
                referencia
                        .get()
                        .addOnSuccessListener {
                            Log.i("log","${it.data}")
                             val firestoreDatosPago = it.toObject(DatosPago::class.java)
                             val firestoreFavoritos = it.toObject(PeliculasFavoritas::class.java)
                                val usuarioFirebase = Usuario(
                                        usuarioLocal.uid,
                                        it.data!!.get("nombre").toString(),
                                        it.data!!.get("apellido").toString(),
                                        usuarioLocal.email!!,
                                        SimpleDateFormat("dd/mm/yyyy").parse(it.data!!.get("fecha").toString()),
                                        it.data!!.get("telefono").toString().toInt(),
                                        arrayListOf(it.data!!.get("favoritos").toString()),
                                        null
                                )
                                AuthUsuario.usuario = usuarioFirebase
                                AuthUsuario.usuario!!.datosPago = firestoreDatosPago!!.Pago
                                AuthUsuario.usuario!!.idPeliculaFav = firestoreFavoritos!!.favoritos

                                val intentR = Intent(this,Menu::class.java)
                                startActivity(intentR)
                            }

                        .addOnFailureListener {
                            Log.i("firebase-firestore","Error")
                        }
            }

        }
    }



    fun pedidoIngresar() {
        val email = findViewById<TextView>(R.id.idEmail)
        val contrasenia = findViewById<TextView>(R.id.idContraseña)
        if( email.text.isNotEmpty()&& contrasenia.text.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.text.toString(),contrasenia.text.toString())
                .addOnSuccessListener {
                            setearUsuarioFirebase()
                            val intentR = Intent(this,Menu::class.java)
                            startActivity(intentR)
                }
                .addOnFailureListener{
                    mostrarAlerta()
                }
        }
        else{
            mostrarAlerta()
        }

    }

    private fun mostrarAlerta(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Html.fromHtml("<font color='#333333'>ERROR</font>"))
            .setMessage(Html.fromHtml("<font color='#333333'>Se ha producido un error al autenticar al usuario</font>"))
            .setPositiveButton("Aceptar",null)
            .create()
            .show()
    }





}