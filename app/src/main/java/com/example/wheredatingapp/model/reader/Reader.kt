package com.example.wheredatingapp.model.reader

import com.example.wheredatingapp.model.Ciudad

interface Reader {

    suspend fun leerCiudades() : List<Ciudad>
}