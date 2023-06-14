package com.example.wheredatingapp.model

import kotlin.math.*

/**
 * Es una data class que almacena puntos geográficos con su latitud y longitud
 */
data class Punto(val latitud : Double, val longitud: Double){
    override fun toString() : String{
        return "Latitud : $latitud Longitud : $longitud"
    }
}

/**
 * Obtiene el punto intermedio entre uno o varios puntos almacenandolo en un objeto de la clase [Punto]
 */
fun Collection<Punto>.puntoMedio() : Punto {

    val latitudMedia = this.latitudes().average()
    val longitudMedia = this.longitudes().average()

    return Punto(latitudMedia,longitudMedia)

}


private fun Collection<Punto>.latitudes() : List<Double>{

    val lisLatitudes = mutableListOf<Double>()

    for(punto in this){

        lisLatitudes.add(punto.latitud)
    }

    return lisLatitudes

}

private fun Collection<Punto>.longitudes() : List<Double>{

    val lisLongitudes = mutableListOf<Double>()

    for(punto in this){

        lisLongitudes.add(punto.longitud)
    }

    return lisLongitudes

}

private fun Pair<Punto, Punto>.occidental(): Punto {
    return this.toList().sortedBy { it.longitud }.first()
}

private fun Pair<Punto, Punto>.oriental(): Punto {
    return this.toList().sortedByDescending { it.longitud }.first()
}

/**
 * Obtiene la distancia en kilómetros entre dos puntos
 */
fun Pair<Punto, Punto>.distancia() : Double {

    val constante = Math.PI / 180

    val latitud1 = this.first.latitud * constante
    val latitud2 = this.second.latitud * constante

    val puntoOriental = this.oriental()
    val puntoOccidental = this.occidental()

    val longitudOriental = puntoOriental.longitud * constante
    val longitudOccidental = puntoOccidental.longitud * constante


    val paso1 = sin(latitud1) * sin(latitud2)


    val paso2 = cos(latitud1) * cos(latitud2)


    val paso3 = cos(abs(longitudOriental - longitudOccidental)) * paso2

    val paso4 = acos(paso3 + paso1)

    return paso4 * 3963 * 1.609344

}

/**
 * Obtiene el coeficiente de validez de una ciudad calculado teniendo en cuenta la distancia y el "tier" (importancia) de la ciudad
 */
fun Pair<Punto,Ciudad>.coeficiente() : Double{

    val distancia = Pair(this.first,this.second.punto()).distancia()

    val tier = this.second.tier

    return distancia * tier.toDouble().pow(2)
}

/**
 * Obtiene la ciudad más próxima a un punto
 */
fun Punto.ciudadMasProxima(lisCiudades: ArrayList<Ciudad>) : Pair<Ciudad?,Int> {

    var ciudadMasProxima : Ciudad? = null
    var distancia = 0

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

/**
 * Obtiene la ciudad con mejor coeficiente de entre una list de ciudades
 */
fun Punto.ciudadMejorCoeficiente(lisCiudades: ArrayList<Ciudad>) : Ciudad {

    var ciudadMejorCoeficiente : Ciudad? = null

    for (ciudad in lisCiudades){

        if (ciudadMejorCoeficiente == null){
            ciudadMejorCoeficiente = ciudad
        }else{

            if (Pair(this,ciudad).coeficiente() < Pair(this,ciudadMejorCoeficiente).coeficiente()){
                ciudadMejorCoeficiente = ciudad
            }

        }

    }

    return ciudadMejorCoeficiente!!

}