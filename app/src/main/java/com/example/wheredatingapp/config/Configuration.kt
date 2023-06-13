package com.example.wheredatingapp.config

import android.content.Context
import com.example.wheredatingapp.R
import java.util.Properties

object Configuration {

    private fun chargeProperties(context : Context) : Properties{

        val inputStream = context.resources.openRawResource(R.raw.conf)

        val properties = Properties()

        properties.load(inputStream)

        inputStream.close()

        return properties
    }

    fun ipServer(context: Context) : String{

        return chargeProperties(context).getProperty("ip")

    }

}