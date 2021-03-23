package com.example.firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment

class EFragmento : AppCompatActivity() {
    lateinit var fragmentoActual: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_e_fragmento)
        val botonPrimer = findViewById<Button>(R.id.btn_primer_fragmento)
        botonPrimer
            .setOnClickListener{
                crearFragmentoUno()
            }

        val botonSegundo = findViewById<Button>(R.id.btn_segundo_fragmento)
        botonSegundo
            .setOnClickListener{
                crearFragmentoDos()
            }

    }

    fun crearFragmentoUno(){
        //Manager
        val fragmenManager = supportFragmentManager
        //Transacciones
        val fragmentTransaction = fragmenManager.beginTransaction()
        //Crear instancia del fragmento
        val primerFragmento = PrimerFragment()
        //Argumentos
        val argumentos = Bundle()
        argumentos.putString("nombre","Bryan")
        argumentos.putInt("edad",23)
        primerFragmento.arguments = argumentos

        //Anadir fragmento
        fragmentTransaction.replace(R.id.rl_fragmento, primerFragmento)
        fragmentoActual = primerFragmento
        fragmentTransaction.commit()
    }

    fun crearFragmentoDos(){
        val fragmenManager = supportFragmentManager
        //Transacciones
        val fragmentTransaction = fragmenManager.beginTransaction()
        //Crear instancia del fragmento
        val segundoFragmento = SegundoFragment()
        //Argumentos
        val argumentos = Bundle()
//        argumentos.putString("nombre","Bryan")
//        argumentos.putInt("edad",23)
        segundoFragmento.arguments = argumentos

        //Anadir fragmento
        fragmentTransaction.replace(R.id.rl_fragmento, segundoFragmento)
        fragmentoActual = segundoFragmento
        fragmentTransaction.commit()

    }
}