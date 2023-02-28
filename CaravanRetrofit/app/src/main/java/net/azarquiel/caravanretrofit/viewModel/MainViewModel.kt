package net.azarquiel.caravanretrofit.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import net.azarquiel.caravanretrofit.api.MainRepository
import net.azarquiel.caravanretrofit.model.*

class MainViewModel: ViewModel() {

    private var repository: MainRepository = MainRepository()

    fun getAllComunidades(): MutableLiveData<List<Comunidad>> {
        val comunidades = MutableLiveData<List<Comunidad>>()
        GlobalScope.launch(Dispatchers.Main) {
            comunidades.value = repository.getAllComunidades()
        }
        return comunidades
    }

    fun getProvinciasByComunidad(idcomunidad: String): MutableLiveData<List<Provincia>> {
        val provincias = MutableLiveData<List<Provincia>>()
        GlobalScope.launch(Dispatchers.Main) {
            provincias.value = repository.getProvinciasByComunidad(idcomunidad)
        }
        return provincias
    }

    fun getMunicipiosByProvincia(idprovincia: String): MutableLiveData<List<Municipio>> {
        val municipios = MutableLiveData<List<Municipio>>()
        GlobalScope.launch(Dispatchers.Main) {
            municipios.value = repository.getMunicipiosByProvincia(idprovincia)
        }
        return municipios
    }

    fun getLugar(latitud: String, longitud : String): MutableLiveData<List<Lugar>> {
        val lugares = MutableLiveData<List<Lugar>>()
        GlobalScope.launch(Dispatchers.Main) {
            lugares.value = repository.getLugar(latitud, longitud)
            Log.d("AAA", lugares.value.toString())
        }

        return lugares
    }

    fun getFotosLugar(idlugar: String): MutableLiveData<List<Foto>> {
        val fotos = MutableLiveData<List<Foto>>()
        GlobalScope.launch(Dispatchers.Main) {
            fotos.value = repository.getFotosLugar(idlugar)
            Log.d("BBB", fotos.value.toString())
        }
        return fotos
    }

    fun getDataUsuarioPorNickPass(nick:String, pass:String): MutableLiveData<Usuario> {
        val usuario = MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            usuario.value = repository.getDataUsuarioPorNickPass(nick, pass)
        }
        return usuario
    }

    fun saveUsuario(usuario: Usuario): MutableLiveData<Usuario> {
        val recursos = MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            recursos.value = repository.saveUsuario(usuario)
        }
        return recursos
    }
}