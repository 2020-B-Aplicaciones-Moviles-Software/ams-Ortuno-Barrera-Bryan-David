package com.example.firebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class BFirestore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b_firestore)
        val botonProdcuto=findViewById<Button>(R.id.btn_producto)
        botonProdcuto.setOnClickListener{
            val intentP = Intent(this,CProducto::class.java)
            startActivity(intentP)
        }
        val botonRestaurante=findViewById<Button>(R.id.btn_restaurante)
        botonRestaurante.setOnClickListener{
            val intentR = Intent(this,CRestaurante::class.java)
            startActivity(intentR)
        }

        val botonIrOrdenes=findViewById<Button>(R.id.btn_ir_ordenes)
        botonIrOrdenes.setOnClickListener{
            val intentR = Intent(this,COrdenes::class.java)
            startActivity(intentR)
        }

        val botonListarOrdenes=findViewById<Button>(R.id.btn_listar_ordenes)
        botonListarOrdenes.setOnClickListener{
            val intentR = Intent(this,COrdenesList::class.java)
            startActivity(intentR)
        }

        val botonAbrirImagenA=findViewById<Button>(R.id.btn_abrir_ImagenA)
        botonAbrirImagenA.setOnClickListener{
            val intentR = Intent(this,DCargarImagen::class.java)
            startActivity(intentR)
        }
    }

}