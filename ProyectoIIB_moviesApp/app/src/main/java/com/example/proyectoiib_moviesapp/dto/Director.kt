package com.example.proyectoiib_moviesapp.dto

import java.util.*

data class Director(
        val idDirector: Int,
        val nombre: String,
        val sexo: String,
        val fechaNacimiento: Date,
        val biografia: String
) {
}