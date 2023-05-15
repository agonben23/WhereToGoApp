package com.example.wheredatingapp.model.reader

import com.example.wheredatingapp.model.Ciudad
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class ReaderTexto(private val text : String) : Reader{

    override suspend fun leerCiudades() : ArrayList<Ciudad>{

        val lisCiudades = arrayListOf<Ciudad>()

        text.split("\n").forEach {
            val datos = it.split(",")
            lisCiudades.add(
                Ciudad(
                    datos[0],
                    datos[1],
                    datos[2].toInt(),
                    datos[3].toDouble(),
                    datos[4].toDouble()
                )
            )
        }

        return lisCiudades
    }



}



