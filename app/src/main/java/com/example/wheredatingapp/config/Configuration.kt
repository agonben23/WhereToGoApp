package com.example.wheredatingapp.config

import android.content.Context
import java.util.Properties

object Configuration {

    private fun chargeProperties(context : Context) : Properties{

        val inputStream = context.assets.open("conf")

        val properties = Properties()

        properties.load(inputStream)

        inputStream.close()

        return properties
    }

    fun ipServer(context: Context) : String{

        return chargeProperties(context).getProperty("ip")

    }

}