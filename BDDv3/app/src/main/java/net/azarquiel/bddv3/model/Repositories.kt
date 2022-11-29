package net.azarquiel.bddv3.model

import android.app.Application
import androidx.lifecycle.LiveData

class GrupoRepositories(application: Application)   {

    val grupoDao= AmigosDB.getDatabase(application)!!.grupoDao()
    //Select
    fun getAllGrupos():LiveData<List<Grupo>>{
        return grupoDao.getAllGrupos()
    }
    //Insert
    suspend fun insert(grupo: Grupo){
        grupoDao.insert(grupo)
    }

    suspend fun delete(grupo: Grupo){
        grupoDao.delete(grupo)
    }
}

class AmigoRepositories(application: Application)   {

    val amigoDao= AmigosDB.getDatabase(application)!!.amigoDao()
    //Select
    fun getAllAmigos():LiveData<List<Amigo>>{
        return amigoDao.getAllAmigos()
    }
    //Insert
    suspend fun insert(amigo: Amigo){
        amigoDao.insert(amigo)
    }

    suspend fun delete(amigo: Amigo){
        amigoDao.delete(amigo)
    }
}