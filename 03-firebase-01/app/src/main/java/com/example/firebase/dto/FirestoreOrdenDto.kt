package com.example.firebase.dto

data class FirestoreOrdenDto(
    var oid:String,
    val oRestaurante:String,
    val oReview:String,
    val oTiposdeComida:String
) {
    override fun toString(): String {
        return "Restaurante ${oRestaurante} , Review ${oReview} , Tipos de domida ${oTiposdeComida}"
    }
}