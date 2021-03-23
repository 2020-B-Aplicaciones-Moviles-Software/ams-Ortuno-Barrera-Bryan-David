package com.example.proyectoiib_moviesapp

import com.example.proyectoiib_moviesapp.dto.Pelicula
import com.example.proyectoiib_moviesapp.dto.Usuario

class AuthPelicula {
    companion object{
        var pelicula: Pelicula?

        init {
            this.pelicula = null;
        }
    }
}