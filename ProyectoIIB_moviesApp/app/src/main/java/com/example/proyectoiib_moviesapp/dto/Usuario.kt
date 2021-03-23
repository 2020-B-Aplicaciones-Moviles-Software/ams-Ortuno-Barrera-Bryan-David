package com.example.proyectoiib_moviesapp.dto

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class Usuario(
    val uid: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val fecha: Date,
    val telefono: Int,
    var idPeliculaFav: ArrayList<String>?,
    var datosPago: ArrayList<String>?
    )