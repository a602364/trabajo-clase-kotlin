package net.azarquiel.caravanretrofit.api

import net.azarquiel.caravanretrofit.model.*

class MainRepository {
    val service = WebAccess.caravanService

    suspend fun getAllComunidades(): List<Comunidad> {
        val webResponse = service.getAllComunidades().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.comunidades
        }
        return emptyList()
    }

    suspend fun getProvinciasByComunidad(idcomunidad : String): List<Provincia> {
        val webResponse = service.getProvinciasByComunidad(idcomunidad).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.provincias
        }
        return emptyList()
    }

    suspend fun getMunicipiosByProvincia(idprovincia : String): List<Municipio> {
        val webResponse = service.getMunicipiosByProvincia(idprovincia).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.municipios
        }
        return emptyList()
    }

    suspend fun getLugar(latitud : String, longitud : String): List<Lugar> {
        val webResponse = service.getLugar(latitud, longitud).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.lugares
        }
        return emptyList()
    }

    suspend fun getFotosLugar(idlugar : String): List<Foto> {
        val webResponse = service.getFotosLugar(idlugar).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.fotos
        }
        return emptyList()
    }

    suspend fun saveUsuario(usuario: Usuario
    ): Usuario? {
        var saveusuario: Usuario? = null
        val webResponse = service.saveUsuario(usuario).await()
        if (webResponse.isSuccessful) {
            saveusuario = webResponse.body()!!.usuario
        }
        return saveusuario
    }

    suspend fun getDataUsuarioPorNickPass(nick: String, pass: String): Usuario? {

        val getUsuario: Usuario? = null
        val webResponse = service.getDataUsuarioPorNickPass(nick, pass).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.usuario
        }
        return getUsuario
    }
}