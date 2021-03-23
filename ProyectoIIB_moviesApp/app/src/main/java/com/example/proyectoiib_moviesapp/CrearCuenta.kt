package com.example.proyectoiib_moviesapp

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.widget.DatePicker
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.*

class CrearCuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        val selectedDate =findViewById<TextView>(R.id.idFechaNacimiento)
        selectedDate.setOnClickListener {
            mostrarFecha()
        }
        val crearCuenta =findViewById<TextView>(R.id.btn_continuar)
        crearCuenta.setOnClickListener {
            crearCuenta()
        }
    }


    fun crearCuenta(){
        val nombre = findViewById<TextView>(R.id.idNombre)
        val apellido = findViewById<TextView>(R.id.idApellido)
        val email = findViewById<TextView>(R.id.idEmail)
        val contrasenia = findViewById<TextView>(R.id.idContraseña)
        val telefono = findViewById<TextView>(R.id.idTelefono)
        val fecha = findViewById<TextView>(R.id.idFechaNacimiento)



        if( email.text.isNotEmpty() &&
            contrasenia.text.isNotEmpty() &&
            nombre.text.isNotEmpty() &&
            apellido.text.isNotEmpty() &&
            telefono.text.isNotEmpty() &&
            fecha.text.isNotEmpty()){
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.text.toString(),contrasenia.text.toString())
                .addOnSuccessListener {
                    val nuevoUsario = hashMapOf<String, Any>(
                        "nombre" to nombre.text.toString(),
                        "apellido" to apellido.text.toString(),
                        "email" to email.text.toString(),
                        "contrasenia" to contrasenia.text.toString(),
                        "telefono" to telefono.text.toString(),
                        "fecha" to fecha.text.toString()
                    )
                    val db = Firebase.firestore
                    val referencia = db.collection("usuario")
                        .document(email.text.toString())
                    referencia
                        .set(nuevoUsario) //Guardar los datos
                        .addOnSuccessListener {
                            Log.i("firebase-firestore","Se creo")
                        }
                        .addOnFailureListener{
                            Log.i("firebase-firestore","fallo")
                        }
                    val intentR = Intent(this,MainActivity::class.java)
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


    private fun mostrarFecha(){
        val mcurrentTime = Calendar.getInstance()
        val year = mcurrentTime.get(Calendar.YEAR)
        val month = mcurrentTime.get(Calendar.MONTH)
        val day = mcurrentTime.get(Calendar.DAY_OF_MONTH)


        val datePicker = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {

            override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                val selectedDate =findViewById<TextView>(R.id.idFechaNacimiento)
                selectedDate.setText(String.format("%d/%d/%d", dayOfMonth, month + 1, year))
            }
        }, year, month, day)
        datePicker.show()
    }

    private fun mostrarAlerta(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(Html.fromHtml("<font color='#333333'>ERROR</font>"))
            .setMessage(Html.fromHtml("<font color='#333333'>Se ha producido un error al crear la cuenta de usuario</font>"))
            .setPositiveButton("Aceptar",null)
            .create()
            .show()
    }
}