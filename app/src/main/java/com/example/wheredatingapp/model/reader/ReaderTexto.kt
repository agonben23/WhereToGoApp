package com.example.wheredatingapp.model.reader

import com.example.wheredatingapp.model.Ciudad
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

class ReaderTexto(private val text : String) : Reader{

    override suspend fun leerCiudades() : ArrayList<Ciudad>{

        val lisCiudades = arrayListOf<Ciudad>()

        text.split("\n").forEach {
            val datos = it.split(",")
            lisCiudades.add(
                Ciudad(
                    datos[0],
                    datos[1].toInt(),
                    datos[2].toDouble(),
                    datos[3].toDouble()
                )
            )
        }

        return lisCiudades
    }



}

object ReaderWeb : Reader{

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override suspend fun leerCiudades(): List<Ciudad> {
        val url = "http://172.26.1.56:8080/api/v1/"

        val retrofit = getRetrofit(url)

        return CiudadesApi(retrofit).retrofitService.getCiudades()
    }

    private fun getRetrofit(url : String) : Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .build()
    }



}

interface MarsApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("ciudades/")
    suspend fun getCiudades(): List<Ciudad>
}

class CiudadesApi(retrofit: Retrofit) {
    val retrofitService: MarsApiService by lazy { retrofit.create(MarsApiService::class.java) }
}
