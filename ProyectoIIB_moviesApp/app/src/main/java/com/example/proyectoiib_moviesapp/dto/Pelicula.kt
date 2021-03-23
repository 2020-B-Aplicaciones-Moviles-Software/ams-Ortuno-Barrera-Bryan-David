package com.example.proyectoiib_moviesapp.dto

import com.google.gson.annotations.SerializedName
import java.sql.Time
import java.util.*
import kotlin.collections.ArrayList

data class  Pelicula (
        @SerializedName("id")
        val idPelicula: Int,
        @SerializedName("title")
        val nombre: String,
        @SerializedName("vote_average")
        val votosPromedio: Double,
        @SerializedName("release_date")
        val fechaLanzamiento: String,
        @SerializedName("runtime")
        val duracion: Int,
        @SerializedName("overview")
        val descripcion: String,
        //val uriVideo: String,
        @SerializedName("poster_path")
        val uriPortada: String,
        @SerializedName("tagline")
        val subTitulo: String,
        @SerializedName("genre_ids")
        val Genero: ArrayList<Int>
//        val actores: ArrayList<Actor>,
//        val director: Director

        )