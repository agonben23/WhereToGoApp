package com.example.wheredatingapp.model.reader

import com.example.wheredatingapp.model.Ciudad
import com.example.wheredatingapp.model.Usuario
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonEncodingException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

object ReaderWeb : Reader{

    private val ipMovil = "192.168.153.236"
    private val ipServer = "172.26.5.118"

    private val url = "http://$ipMovil:8080/api/v1/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    override suspend fun leerCiudades(): List<Ciudad> {

        val retrofit = getRetrofit(url)

        return CiudadesApi(retrofit).retrofitService.getCiudades()
    }

    suspend fun buscarUsuario(userBusqueda: Usuario): Usuario? {
        val retrofit = getRetrofit(url)

        return UsuariosApi(retrofit).retrofitService.getUser(userBusqueda)
    }

    suspend fun insertUsuario(user: Usuario): Usuario? {
        val retrofit = getRetrofit(url)

        return UsuariosApi(retrofit).retrofitService.insertUser(user)
    }

    private fun getRetrofit(url : String) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .build()
    }



}

interface ApiServiceCiudades {
    /**
     * Returns a [List] of [Ciudad] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "ciudades" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("ciudades/all")
    suspend fun getCiudades(): List<Ciudad>
}

interface ApiServiceUsuarios {
    /**
     * Returns a [List] of [Ciudad] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "ciudades" endpoint will be requested with the GET
     * HTTP method
     */

    @GET("usuarios/all")
    suspend fun getUsers(): List<Usuario>
    @POST("usuarios/")
    suspend fun insertUser(@Body user : Usuario) : Usuario?

    @POST("usuarios/one")
    suspend fun getUser(@Body userBusqueda: Usuario): Usuario?
}

class CiudadesApi(retrofit: Retrofit) {
    val retrofitService: ApiServiceCiudades by lazy { retrofit.create(ApiServiceCiudades::class.java) }
}

class UsuariosApi(retrofit: Retrofit) {
    val retrofitService: ApiServiceUsuarios by lazy { retrofit.create(ApiServiceUsuarios::class.java) }
}
