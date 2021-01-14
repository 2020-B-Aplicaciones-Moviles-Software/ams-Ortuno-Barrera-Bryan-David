package com.example.examen1b.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

@Entity
data class Marca(

        val nombre: String,
        val fechaFundacion: Date?,
        val empresa: String,
        val pais: String,
        @PrimaryKey(autoGenerate = true)
        val idMarca: Int = 0

)