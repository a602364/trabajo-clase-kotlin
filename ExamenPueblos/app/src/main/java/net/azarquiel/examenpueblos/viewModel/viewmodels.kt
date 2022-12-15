package net.azarquiel.examenpueblos.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.examenpueblos.model.*

class ComunidadViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: ComunidadRepository = ComunidadRepository(application)

    fun getAllComunidades(): LiveData<List<Comunidad>>{
        return repository.getAllComunidades()
    }

}

class ProvinciaViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: ProvinciaRepository = ProvinciaRepository(application)

    fun getProvinciasByComunidad(comunidadid: Int): LiveData<List<ProvinciaView>>{
        return repository.getProvinciasByComunidad(comunidadid)
    }

}

class PuebloViewModel (application: Application) : AndroidViewModel(application) {

    private var repository: PuebloRepository = PuebloRepository(application)

    fun getPueblosByProvincia(provinciaid: Int): LiveData<List<PuebloView>>{
        return repository.getPueblosByProvincia(provinciaid)
    }

    fun getPueblosByComunidad(comunidadid: Int): LiveData<List<PuebloView>>{
        return repository.getPueblosByComunidad(comunidadid)
    }

    fun getPueblosFav(comunidadid: Int): LiveData<List<PuebloView>>{
        return repository.getPueblosFav(comunidadid)
    }

    fun update(id: Int) {
        GlobalScope.launch() {
            repository.update(id)
            launch(Dispatchers.Main) {
            }
        }
    }

}
