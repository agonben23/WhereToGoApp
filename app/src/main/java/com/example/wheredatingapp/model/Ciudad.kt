package com.example.wheredatingapp.model

import com.example.wheredatingapp.Punto


data class Ciudad(val nombre: String,val tier : Int, val latitud : Double, val longitud : Double) {


fun punto() : Punto{
    return Punto(latitud,longitud)
}

}