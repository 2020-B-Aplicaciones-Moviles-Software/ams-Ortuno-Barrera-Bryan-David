package com.example.proyectoiib_moviesapp.dto

import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.google.gson.annotations.SerializedName

data class ActorResponse(
    val id: Int,
    @SerializedName("cast")
    val actorCast: List<Actor>
)