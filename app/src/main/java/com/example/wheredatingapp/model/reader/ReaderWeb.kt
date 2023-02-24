package com.example.wheredatingapp.model.reader

import com.example.wheredatingapp.model.Ciudad
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

object ReaderWeb : Reader{

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    override suspend fun leerCiudades(): List<Ciudad> {
        val url = "http://172.26.5.118:8080/api/v1/"

        val retrofit = getRetrofit(url)

        return CiudadesApi(retrofit).retrofitService.getCiudades()
    }

    private fun getRetrofit(url : String) : Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(url)
            .build()
    }



}

interface ApiService {
    /**
     * Returns a [List] of [MarsPhoto] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "photos" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("ciudades/all")
    suspend fun getCiudades(): List<Ciudad>
}

class CiudadesApi(retrofit: Retrofit) {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}
