package com.example.wheredatingapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wheredatingapp.model.Ciudad
import com.example.wheredatingapp.model.reader.ReaderWeb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchMidpointCityActivity : AppCompatActivity() {

    private var lisCiudades: List<Ciudad> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_midpoint_city)

        val scope = CoroutineScope(Job() + Dispatchers.Main)

        val readerWeb = ReaderWeb(this)

        scope.launch {
            lisCiudades = readerWeb.leerCiudades()
        }

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

            val ciudadMasProxima = puntoMedio.ciudadMasProxima(lisCiudades as ArrayList<Ciudad>)
            val ciudadMejorCoeficiente = puntoMedio.ciudadMejorCoeficiente(lisCiudades as ArrayList<Ciudad>)

            if (ciudadMasProxima.first != null){
                setMapLocatitions(listOf(ciudad1,ciudad2),ciudadMasProxima.first!!,ciudadMasProxima.second,ciudadMejorCoeficiente)
            }

        }
    }

    private fun setMapLocatitions(lisCiudades : List<Ciudad?>, ciudadMasProxima : Ciudad ,distancia : Int , ciudadMejorCoeficiente : Ciudad){

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

            intent.putExtra("DistanciaPMCM",distancia)

            intent.putExtra("CiudadMejorCoeficiente",ciudadMejorCoeficiente.nombre)
            intent.putExtra("LatitudCiudadMejorCoeficiente",ciudadMejorCoeficiente.latitud)
            intent.putExtra("LongitudCiudadMejorCoeficiente",ciudadMejorCoeficiente.longitud)

            startActivity(intent)
        }

    }

    private fun getCiudad(idEditText : Int) : Ciudad?{
        val editText : EditText = findViewById(idEditText)

        val ciudadNombre = editText.text

        return lisCiudades.find { it.nombre == ciudadNombre.toString() }
    }

    private fun setPosition(idEditText : Int,idLat : Int,idLong: Int){
        val textLat: TextView = findViewById(idLat)
        val textLong: TextView = findViewById(idLong)

        val editText : EditText = findViewById(idEditText)

        val ciudadNombre = editText.text

        if (lisCiudades.any { it.nombre == ciudadNombre.toString() }){
            val ciudad = lisCiudades.first{it.nombre == ciudadNombre.toString()}

            textLat.text = ciudad.latitud.toString()
            textLong.text = ciudad.longitud.toString()
        }else{
            Toast.makeText(this, "Ciudad no encontrada", Toast.LENGTH_SHORT).show()
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