package com.example.proyectoiib_moviesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.proyectoiib_moviesapp.dataVideo.api.POSTER_BASE_URL
import id.ionbit.ionalert.IonAlert

class ConfirmarPedido : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pedido)

        val precio: Double = intent.getDoubleExtra("precio",0.00)
        val distancia: Float = intent.getFloatExtra("distancia", 0.00f)
        val direccion: String? = intent.getStringExtra("direccion")
        val precioT = 8.00 + precio

        findViewById<TextView>(R.id.movie_title).text = AuthPelicula.pelicula!!.nombre
        findViewById<TextView>(R.id.direccion).text = direccion
        findViewById<TextView>(R.id.Distancia).text = distancia.toString() + "km"
        findViewById<TextView>(R.id.priceP).text = "$8.00"
        findViewById<TextView>(R.id.priceE).text = "$${precio.toString()}"
        findViewById<TextView>(R.id.priceT).text = "$${precioT}"

        val moviePosterURL = POSTER_BASE_URL + AuthPelicula.pelicula!!.uriPortada
        Glide.with(this)
            .load(moviePosterURL)
            .into(findViewById<ImageView>(R.id.iv_movie_poster))

        val btn_confirmar = findViewById<Button>(R.id.btn_confirmar)
        btn_confirmar.setOnClickListener {
            accionCompletada()
        }
    }


    fun accionCompletada(){
        IonAlert(this, IonAlert.SUCCESS_TYPE)
                .setTitleText("¡Buen trabajo!")
                .setContentText("Acción completada con éxito!")
                .show()
    }
}