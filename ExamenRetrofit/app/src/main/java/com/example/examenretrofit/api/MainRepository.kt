package com.example.examenretrofit.api

import android.util.Log
import com.example.examenretrofit.model.Categoria
import com.example.examenretrofit.model.Chiste
import com.example.examenretrofit.model.Punto
import com.example.examenretrofit.model.Usuario

class MainRepository() {
    val service = WebAccess.chistesService

    suspend fun getAllCategorias(): List<Categoria> {
        val webResponse = service.getAllCategorias().await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.categorias
        }
        return emptyList()
    }


    suspend fun getChistesByCategoria(idcategoria : String): List<Chiste> {
        val webResponse = service.getChistesByCategoria(idcategoria).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.chistes
        }
        return emptyList()
    }

    suspend fun saveChiste(chiste: Chiste
    ): Chiste? {
        var savechiste: Chiste? = null
        val webResponse = service.saveChiste(chiste).await()
        if (webResponse.isSuccessful) {
            savechiste = webResponse.body()!!.chiste
        }
        return savechiste
    }

    suspend fun getAvgChiste(idchiste: Long): Int {
        val webResponse = service.getAvgChiste(idchiste).await()
        if (webResponse.isSuccessful) {
            return webResponse.body()!!.avg
        }
        return 0
    }

    suspend fun savePuntoChiste(idchiste: Long, punto: Punto
    ): Punto? {
        var puntoresponse:Punto? = null
        val webResponse = service.savePuntoChiste(idchiste, punto).await()
        if (webResponse.isSuccessful) {
            puntoresponse = webResponse.body()!!.punto
        }
        return puntoresponse
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