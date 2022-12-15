package net.azarquiel.examenpueblos.model

import android.app.Application
import androidx.lifecycle.LiveData

class ComunidadRepository(application: Application) {

    val comunidadDao = PueblosBonitosDB.getDatabase(application)!!.comunidadDao()

    fun getAllComunidades(): LiveData<List<Comunidad>> {
        return comunidadDao.getAllComunidades()
    }

}

class ProvinciaRepository(application: Application) {

    val provinciaDao = PueblosBonitosDB.getDatabase(application)!!.provinciaDao()

    fun getProvinciasByComunidad(comunidadid: Int): LiveData<List<ProvinciaView>> {
        return provinciaDao.getAllProvincias(comunidadid)
    }

}

class PuebloRepository(application: Application) {

    val puebloDao = PueblosBonitosDB.getDatabase(application)!!.puebloDao()

    fun getPueblosByProvincia(provinciaid: Int): LiveData<List<PuebloView>> {
        return puebloDao.getAllPueblos(provinciaid)
    }

    fun getPueblosByComunidad(comunidadid: Int): LiveData<List<PuebloView>> {
        return puebloDao.getPueblosByComunidad(comunidadid)
    }

    fun getPueblosFav(comunidadid: Int): LiveData<List<PuebloView>> {
        return puebloDao.getPueblosFav(comunidadid)
    }



    // update
    fun update(id: Int) {
        puebloDao.updateFavPueblo(id)
    }

}
