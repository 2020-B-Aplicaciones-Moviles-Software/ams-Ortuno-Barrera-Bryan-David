package com.example.proyectoiib_moviesapp.dto

import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val page: Int,
    @SerializedName("results")
    val movieList: List<Pelicula>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)