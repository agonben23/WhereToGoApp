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

/**
 * Esta clase permite leer los datos provenientes de la API REST
 * @property ipServer Es la ip del servidor a donde llama la aplicación
 * @property url Es la url de la petición HTTP al servidor
 * @property moshi Es una instancia de la clase [Moshi], la cual permite el mapeo objeto-relacional
 */
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

    /**
     * Crea una instancia de la clase [Retrofit] para realizar la llamada HTTP
     * @return Una instancia de la clase [Retrofit]
     */
    private fun getRetrofit(url : String) : Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .build()
    }



}

interface ApiServiceCiudades {

    /**
     * Devuelve una [List] de objetos [Ciudad].
     * Este metodo puede llamarse con una corrutina
     */
    @GET("ciudades/all")
    suspend fun getCiudades(): List<Ciudad>
}

interface ApiServiceUsuarios {

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
