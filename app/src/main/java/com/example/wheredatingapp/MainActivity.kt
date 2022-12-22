package com.example.wheredatingapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
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

        buttonSearch1.setOnClickListener { setPosition(R.id.editTextTextPersonName,R.id.lat1,R.id.lon1) }
        buttonSearch2.setOnClickListener { setPosition(R.id.editTextTextPersonName2,R.id.lat2,R.id.lon2) }
        buttonCalculate.setOnClickListener { setMapLocatition() }
    }

    fun setMapLocatition(){

        val intent = Intent(this,MapsActivity::class.java)

        val punto = calculatePosition()

        intent.putExtra("latitudResultado",punto.latitud)
        intent.putExtra("longitudResultado",punto.longitud)

        startActivity(intent)

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