package com.example.examen1b.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Celular(

        val nombre: String,
        val anio: Date,
        val precio: Double,
        val lectorDeHuellas: Boolean,
        val pixelesCamara: Int,
        val idMarca:Int,
        @PrimaryKey(autoGenerate = true)
        val idCelular: Int = 0

)
