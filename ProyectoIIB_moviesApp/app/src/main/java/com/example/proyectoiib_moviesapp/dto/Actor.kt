package com.example.proyectoiib_moviesapp.dto

import com.google.gson.annotations.SerializedName

data class Actor(
        @SerializedName("id")
        val idActor: Int,
        @SerializedName("name")
        val nombre: String,
        @SerializedName("gender")
        val sexo: Int,
        @SerializedName("profile_path")
        val foto: String,
        @SerializedName("character")
        val personaje: String
        )