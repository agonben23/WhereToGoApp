package com.example.wheredatingapp

import com.example.wheredatingapp.model.Ciudad
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch1 : Button = findViewById(R.id.buttonSearch1)
        val buttonSearch2 : Button = findViewById(R.id.buttonSearch2)
        val buttonCalculate : Button = findViewById(R.id.buttonCalculate)

        var ciudad1 : Ciudad? = getCiudad(R.id.editText)
        var ciudad2 : Ciudad? = getCiudad(R.id.editText2)

        buttonSearch1.setOnClickListener {
            setPosition(R.id.editText,R.id.lat1,R.id.lon1)
            ciudad1 = getCiudad(R.id.editText)
        }
        buttonSearch2.setOnClickListener {
            setPosition(R.id.editText2,R.id.lat2,R.id.lon2)
            ciudad2 = getCiudad(R.id.editText2)
        }
        buttonCalculate.setOnClickListener {

            val puntoMedio = calculatePosition()

            val lisCiudades = ReaderCiudades(this.resources.openRawResource(R.raw.ciudades).bufferedReader().readText()).leerCiudades()

            val ciudadMasProxima = puntoMedio.ciudadMasProxima(lisCiudades)

            if (ciudadMasProxima != null){
                setMapLocatitions(listOf(ciudad1,ciudad2),ciudadMasProxima)
            }

        }
    }

    private fun setMapLocatitions(lisCiudades : List<Ciudad?>, ciudadMasProxima : Ciudad){

        if (lisCiudades.all { it != null }) {

            val intent = Intent(this, MapsActivity::class.java)

            val punto = calculatePosition()

            intent.putExtra("latitudResultado", punto.latitud)
            intent.putExtra("longitudResultado", punto.longitud)

            var contadorCiudades = 1

            for (ciudad in lisCiudades){
                intent.putExtra("ciudad$contadorCiudades",ciudad?.nombre)
                intent.putExtra("latitudCiudad$contadorCiudades", ciudad?.latitud)
                intent.putExtra("longitudCiudad$contadorCiudades", ciudad?.longitud)
                contadorCiudades++
            }

            intent.putExtra("CiudadMasProxima",ciudadMasProxima.nombre)
            intent.putExtra("LatitudCiudadMasProxima",ciudadMasProxima.latitud)
            intent.putExtra("LongitudCiudadMasProxima",ciudadMasProxima.longitud)

            startActivity(intent)
        }

    }

    private fun getCiudad(idEditText : Int) : Ciudad?{
        val editText : EditText = findViewById(idEditText)

        val ciudadNombre = editText.text

        val lisCiudades = ReaderCiudades(this.resources.openRawResource(R.raw.ciudades).bufferedReader().readText()).leerCiudades()

        return lisCiudades.find { it.nombre == ciudadNombre.toString() }
    }

    private fun setPosition(idEditText : Int,idLat : Int,idLong: Int){
        val textLat: TextView = findViewById(idLat)
        val textLong: TextView = findViewById(idLong)

        val editText : EditText = findViewById(idEditText)

        val ciudadNombre = editText.text

        val lisCiudades = ReaderCiudades(this.resources.openRawResource(R.raw.ciudades).bufferedReader().readText()).leerCiudades()

        if (lisCiudades.any { it.nombre == ciudadNombre.toString() }){
            val ciudad = lisCiudades.first{it.nombre == ciudadNombre.toString()}

            textLat.text = ciudad.latitud.toString()
            textLong.text = ciudad.longitud.toString()
        }else{
            Toast.makeText(this, "com.example.wheredatingapp.model.Ciudad no encontrada", Toast.LENGTH_SHORT).show()
        }

    }

    @SuppressLint("SetTextI18n")
    private fun calculatePosition() : Punto{

        val textLat1: TextView = findViewById(R.id.lat1)
        val textLong1: TextView = findViewById(R.id.lon1)

        val textLat2: TextView = findViewById(R.id.lat2)
        val textLong2: TextView = findViewById(R.id.lon2)

        val punto1 = Punto(textLat1.text.toString().toDouble(),textLong1.text.toString().toDouble())
        val punto2 = Punto(textLat2.text.toString().toDouble(),textLong2.text.toString().toDouble())

        return listOf(punto1,punto2).puntoMedio()

    }


}