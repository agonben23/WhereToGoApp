package com.example.wheredatingapp

import android.content.Context
import android.widget.Toast

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

}