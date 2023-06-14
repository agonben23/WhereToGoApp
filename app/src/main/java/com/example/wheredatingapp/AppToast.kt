package com.example.wheredatingapp

import android.content.Context
import android.widget.Toast
import java.net.SocketTimeoutException

/**
 * Es un objeto que almacena varios objetos [Toast] predeterminados listos para ser utilizados en la aplicación
 */
object AppToast{

    fun inDevelloping(context : Context){
        Toast.makeText(context, "En desarrollo", Toast.LENGTH_SHORT).show()
    }

    fun registrationError(context: Context){
        Toast.makeText(context, "Error en el registro del usuario (ya existe un usuario con ese nick o correo electrónico)", Toast.LENGTH_SHORT).show()
    }

    fun lugaresNotFound(context: Context){
        Toast.makeText(context, "No se ha encontrado ningún lugar para visitar en esa localidad", Toast.LENGTH_SHORT).show()
    }

    fun noInternetConnection(context: Context){
        Toast.makeText(context,"Sin conexión a internet",Toast.LENGTH_LONG).show()
    }

    fun userNotFound(context: Context){
        Toast.makeText(context,"Dato(s) incorrecto(s)",Toast.LENGTH_SHORT).show()
    }

    fun checkInternetConection(context: Context, action : () -> Unit){

        try{
            action.invoke()
        }catch (e : Exception){
            noInternetConnection(context)
        }
    }

}