package com.example.wheredatingapp.model.reader

import android.content.Context
import com.example.wheredatingapp.config.Configuration
import com.example.wheredatingapp.model.Ciudad
import com.example.wheredatingapp.model.Lugar
import com.example.wheredatingapp.model.Usuario
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

class ReaderWeb(context: Context) {

    private val ipServer = Configuration.ipServer(context)

    private val url = "http://$ipServer:8080/api/v1/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    suspend fun leerCiudades(): List<Ciudad> {

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

    suspend fun leerLugaresbyCiudad(ciudad: String): List<Lugar>?{
        val retrofit = getRetrofit(url)

        return LugaresApi(retrofit).retrofitService.getLugaresbyCity(ciudad)
    }

    private fun getRetrofit(url : String) : Retrofit {

        val client = OkHttpClient.Builder()
            .callTimeout(0,TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .client(client)
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

interface ApiServiceLugares {

    @POST("lugares/bycity")
    suspend fun getLugaresbyCity(@Body ciudad : String) : List<Lugar>?

}

class CiudadesApi(retrofit: Retrofit) {
    val retrofitService: ApiServiceCiudades by lazy { retrofit.create(ApiServiceCiudades::class.java) }
}

class UsuariosApi(retrofit: Retrofit) {
    val retrofitService: ApiServiceUsuarios by lazy { retrofit.create(ApiServiceUsuarios::class.java) }
}

class LugaresApi(retrofit: Retrofit){
    val retrofitService: ApiServiceLugares by lazy { retrofit.create(ApiServiceLugares::class.java) }
}
