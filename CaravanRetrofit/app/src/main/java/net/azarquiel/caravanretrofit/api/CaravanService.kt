package net.azarquiel.caravanretrofit.api

import kotlinx.coroutines.Deferred
import net.azarquiel.caravanretrofit.model.Respuesta
import net.azarquiel.caravanretrofit.model.Usuario
import retrofit2.Response
import retrofit2.http.*

interface CaravanService {
    @GET("comunidad")
    fun getAllComunidades(): Deferred<Response<Respuesta>>

    @GET("comunidad/{idcomunidad}/provincia")
    fun getProvinciasByComunidad(@Path("idcomunidad") idcomunidad: String): Deferred<Response<Respuesta>>

    @GET("provincia/{idprovincia}/municipio")
    fun getMunicipiosByProvincia(@Path("idprovincia") idprovincia: String): Deferred<Response<Respuesta>>

    @GET("lugar")
    fun getLugar(
        @Query("latitud") latitud: String,
        @Query("longitud") longitud: String
    ): Deferred<Response<Respuesta>>

    @GET("lugar/{idlugar}/fotos")
    fun getFotosLugar(
        @Path("idlugar") idlugar : String
    ): Deferred<Response<Respuesta>>

    @GET("usuario")
    fun getDataUsuarioPorNickPass(
        @Query("nick") nick: String,
        @Query("pass") pass: String
    ): Deferred<Response<Respuesta>>

    @POST("usuario")
    fun saveUsuario(@Body usuario: Usuario): Deferred<Response<Respuesta>>
}