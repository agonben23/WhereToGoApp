package com.example.wheredatingapp.model

/**
 * Es una data class que almacena los lugares para visitar
 */
data class Lugar (
    val nombre : String,
    val ciudad: Ciudad,
    val uriImagen : String,
    val descripcion : String
        )