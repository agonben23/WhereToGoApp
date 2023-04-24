package com.example.wheredatingapp.model.reader

import com.example.wheredatingapp.model.Ciudad
import com.example.wheredatingapp.model.Usuario
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object ReaderWeb : Reader{

    private const val url = "http://172.26.5.118:8080/api/v1/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override suspend fun leerCiudades(): List<Ciudad> {

        val retrofit = getRetrofit(url)

        return CiudadesApi(retrofit).retrofitService.getCiudades()
    }

    suspend fun buscarUsuario(userBusqueda: Usuario): Usuario?{
        val retrofit = getRetrofit(url)

        val lisUsuarios = UsuariosApi(retrofit).retrofitService.getUsers()


        return lisUsuarios.find { (it.mail == userBusqueda.mail || it.nick == userBusqueda.nick) && it.password == userBusqueda.password }
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
}

class CiudadesApi(retrofit: Retrofit) {
    val retrofitService: ApiServiceCiudades by lazy { retrofit.create(ApiServiceCiudades::class.java) }
}

class UsuariosApi(retrofit: Retrofit) {
    val retrofitService: ApiServiceUsuarios by lazy { retrofit.create(ApiServiceUsuarios::class.java) }
}
