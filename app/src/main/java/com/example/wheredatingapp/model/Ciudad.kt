package com.example.wheredatingapp.model

/**
 * Esa data class que almacena las ciudades
 */
data class Ciudad(val nombre: String = "",val provincia : String = "", val tier : Int = 5, val latitud : Double = 0.0, val longitud : Double = 0.0) {


fun punto() : Punto {
    return Punto(latitud,longitud)
}

}