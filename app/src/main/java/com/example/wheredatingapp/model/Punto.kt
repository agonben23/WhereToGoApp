package com.example.wheredatingapp

import com.example.wheredatingapp.model.Ciudad
import kotlin.math.*

data class Punto(val latitud : Double, val longitud: Double){
    override fun toString() : String{
        return "Latitud : $latitud Longitud : $longitud"
    }
}


fun Collection<Punto>.puntoMedio() : Punto{

    val latitudMedia = this.latitudes().average()
    val longitudMedia = this.longitudes().average()

    return Punto(latitudMedia,longitudMedia)

}

fun Collection<Punto>.latitudes() : List<Double>{

    val lisLatitudes = mutableListOf<Double>()

    for(punto in this){

        lisLatitudes.add(punto.latitud)
    }

    return lisLatitudes

}

fun Collection<Punto>.longitudes() : List<Double>{

    val lisLongitudes = mutableListOf<Double>()

    for(punto in this){

        lisLongitudes.add(punto.longitud)
    }

    return lisLongitudes

}

fun Pair<Punto,Punto>.occidental(): Punto {
    return this.toList().sortedBy { it.longitud }.first()
}

fun Pair<Punto,Punto>.oriental(): Punto {
    return this.toList().sortedByDescending { it.longitud }.first()
}

fun Pair<Punto, Punto>.distancia() : Double {

    val constante = Math.PI / 180

    val longitud1 = this.first.longitud * constante
    val longitud2 = this.second.longitud * constante
    val latitud1 = this.first.latitud * constante
    val latitud2 = this.second.latitud * constante

    val puntoOriental = this.oriental()
    val puntoOccidental = this.occidental()

    val longitudOriental = puntoOriental.longitud * constante
    val longitudOccidental = puntoOccidental.longitud * constante

    //Paso 1 : Multiplicar el seno de la latitud del primer punto por el seno de la latitud del segundo punto
    val paso1 = sin(latitud1) * sin(latitud2)

    //Paso 2 :
    val paso2 = cos(latitud1) * cos(latitud2)


    val paso3 = cos(abs(longitudOriental - longitudOccidental)) * paso2

    val paso4 = acos(paso3 + paso1)

    return paso4 * 3963 * 1.609344

}

fun Punto.ciudadMasProxima(lisCiudades: ArrayList<Ciudad>) : Pair<Ciudad?,Int> {

    var ciudadMasProxima : Ciudad? = null
    var distancia : Int = 0

    for (ciudad in lisCiudades){

        val punto = ciudad.punto()

        if (ciudadMasProxima == null){
            ciudadMasProxima = ciudad
        }else{

            if (Pair(this,punto).distancia() < Pair(this,ciudadMasProxima.punto()).distancia()){
                ciudadMasProxima = ciudad
                distancia = Pair(this,punto).distancia().roundToInt()
            }

        }

    }

    return Pair(ciudadMasProxima,distancia)

}