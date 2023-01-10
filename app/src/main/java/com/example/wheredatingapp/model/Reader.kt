package com.example.wheredatingapp

import com.example.wheredatingapp.model.Ciudad

class ReaderCiudades(private val text : String) {

    fun leerCiudades() : ArrayList<Ciudad>{

        val lisCiudades = arrayListOf<Ciudad>()

        text.split("\n").forEach {
            val datos = it.split(",")
            lisCiudades.add(
                Ciudad(
                    datos[0],
                    datos[1].toInt(),
                    datos[2].toDouble(),
                    datos[3].toDouble()
                )
            )
        }

        return lisCiudades
    }



}
