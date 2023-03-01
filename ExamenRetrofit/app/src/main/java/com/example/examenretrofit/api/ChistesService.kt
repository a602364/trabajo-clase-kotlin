package com.example.examenretrofit.api

import com.example.examenretrofit.model.Chiste
import com.example.examenretrofit.model.Punto
import com.example.examenretrofit.model.Respuesta
import com.example.examenretrofit.model.Usuario
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.*

interface ChistesService {
    @GET("categorias")
    fun getAllCategorias(): Deferred<Response<Respuesta>>

    @GET("categoria/{idcategoria}/chistes")
    fun getChistesByCategoria(@Path("idcategoria") idcategoria: String): Deferred<Response<Respuesta>>

    @POST("chiste")
    fun saveChiste(@Body chiste: Chiste): Deferred<Response<Respuesta>>

    @GET("chiste/{idchiste}/avgpuntos")
    fun getAvgChiste(@Path("idchiste") idchiste: Long): Deferred<Response<Respuesta>>

    @POST("chiste/{idchiste}/punto")
    fun savePuntoChiste(
        @Path("idchiste") idchiste: Long,
        @Body punto: Punto
    ): Deferred<Response<Respuesta>>

    @GET("usuario")
    fun getUserByNickAndPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Respuesta>>

    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>

    @GET("usuario")
    fun getDataUsuarioPorNickPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String): Deferred<Response<Respuesta>>
}