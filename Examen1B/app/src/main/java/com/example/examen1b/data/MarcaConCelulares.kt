package com.example.examen1b.data

import androidx.room.Embedded
import androidx.room.Relation

data class MarcaConCelulares(
    @Embedded val marca: Marca,
    @Relation(
        parentColumn = "idMarca",
        entityColumn = "idMarca"
    )
    val celular: List<Celular>

)
