package com.example.wheredatingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.wheredatingapp.databinding.ActivityMapsBinding
import com.example.wheredatingapp.ui.Icons
import com.example.wheredatingapp.ui.getMarkerIcon
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * Es la pantalla de resultado de la búsqueda de ciudddes para quedar
 */
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipula el mapa una vez disponible.
     * Carga los marcadores con los resultados de la búsqueda
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val bundle = intent.extras

        val latitud = bundle?.get("latitudResultado") as Double
        val longitud = bundle.get("longitudResultado") as Double

        val puntoMedio = LatLng(latitud, longitud)
        mMap.addMarker(MarkerOptions().position(puntoMedio).title("Punto Medio").icon(
            getMarkerIcon("#51f407")
        ))

        var ciudadEncontrada = true
        var contador = 1

        do {

            val nombreCiudad = bundle.get("ciudad$contador")

            val latitudCiudad = bundle.get("latitudCiudad$contador")
            val longitudCiudad = bundle.get("longitudCiudad$contador")


            if (nombreCiudad != null && latitudCiudad != null && longitudCiudad != null){
                val punto = LatLng(latitudCiudad as Double,longitudCiudad as Double)

                mMap.addMarker(MarkerOptions().position(punto).title(nombreCiudad.toString()))
            }else{
                ciudadEncontrada = false
            }

            contador++

        }while (ciudadEncontrada)

        val ciudadMasProxima = bundle.get("CiudadMasProxima")
        val latitudCiudadMasProxima = bundle.get("LatitudCiudadMasProxima")
        val longitudCiudadMasProxima = bundle.get("LongitudCiudadMasProxima")

        val distanciaPuntoMedioCiudadMasProxima = bundle.get("DistanciaPMCM") as Int

        if (ciudadMasProxima != null && latitudCiudadMasProxima != null && longitudCiudadMasProxima != null){
            val puntoCiudadMasProximaAlPuntoMedio = LatLng(latitudCiudadMasProxima as Double,longitudCiudadMasProxima as Double)

            mMap.addMarker(MarkerOptions().position(puntoCiudadMasProximaAlPuntoMedio).title("Ciudad más proxima : $ciudadMasProxima Distancia  : $distanciaPuntoMedioCiudadMasProxima km").icon(
                getMarkerIcon("#ff2299")
            ))
        }



        val ciudadMejorCoeficiente = bundle.get("CiudadMejorCoeficiente")
        val latitudCiudadMejorCoeficiente = bundle.get("LatitudCiudadMejorCoeficiente")
        val longitudCiudadMejorCoeficiente = bundle.get("LongitudCiudadMejorCoeficiente")

        if (ciudadMejorCoeficiente != null && latitudCiudadMejorCoeficiente != null && longitudCiudadMejorCoeficiente != null){
            val puntoCiudadMejorCoeficiente = LatLng(latitudCiudadMejorCoeficiente as Double,longitudCiudadMejorCoeficiente as Double)

            mMap.addMarker(MarkerOptions().position(puntoCiudadMejorCoeficiente).title("Ciudad mejor situada : $ciudadMejorCoeficiente").icon(
                getMarkerIcon("#ff2299")
            ))
        }





        mMap.moveCamera(CameraUpdateFactory.newLatLng(puntoMedio))

        mMap.uiSettings.isZoomControlsEnabled = true
    }
}