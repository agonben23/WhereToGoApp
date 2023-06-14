package com.example.wheredatingapp.config

import android.content.Context
import com.example.wheredatingapp.R
import java.util.Properties

/**
 * Este objeto permite acceder a la configuración del proyecto
 */
object Configuration {

    /**
     * Lee el archivo de configuración y cargar las propiedades a un objeto de la clase [Properties]
     */
    private fun chargeProperties(context : Context) : Properties{

        val inputStream = context.resources.openRawResource(R.raw.conf)

        val properties = Properties()

        properties.load(inputStream)

        inputStream.close()

        return properties
    }

    /**
     * Carga la IP del Servidor al que realizará las peticiones HTTP
     */
    fun ipServer(context: Context) : String{

        return chargeProperties(context).getProperty("ip")

    }

}