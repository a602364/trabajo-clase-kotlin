package com.example.examenretrofit.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examenretrofit.api.MainRepository
import com.example.examenretrofit.model.Categoria
import com.example.examenretrofit.model.Chiste
import com.example.examenretrofit.model.Punto
import com.example.examenretrofit.model.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainViewModel : ViewModel(){

    private var repository: MainRepository = MainRepository()

    fun getAllCategorias(): MutableLiveData<List<Categoria>> {
        val categorias = MutableLiveData<List<Categoria>>()
        GlobalScope.launch(Dispatchers.Main) {
            categorias.value = repository.getAllCategorias()

        }
        return categorias
    }

    fun getChistesByCategoria(idCategoria: String): MutableLiveData<List<Chiste>> {
        val chistes = MutableLiveData<List<Chiste>>()
        GlobalScope.launch(Dispatchers.Main) {
            chistes.value = repository.getChistesByCategoria(idCategoria)
        }
        return chistes
    }

    fun saveChiste(chiste: Chiste): MutableLiveData<Chiste> {
        val recursos = MutableLiveData<Chiste>()
        GlobalScope.launch(Dispatchers.Main) {
            recursos.value = repository.saveChiste(chiste)
        }
        return recursos
    }

    fun getAvgChiste(idchiste: Long): MutableLiveData<Int> {
        val avg = MutableLiveData<Int>()
        GlobalScope.launch(Dispatchers.Main) {
            avg.value = repository.getAvgChiste(idchiste)
        }
        return avg
    }

    fun savePuntoChiste(idchiste: Long, punto: Punto): MutableLiveData<Punto> {
        val puntoresponse = MutableLiveData<Punto>()
        GlobalScope.launch(Dispatchers.Main) {
            puntoresponse.value = repository.savePuntoChiste(idchiste, punto)
        }
        return puntoresponse
    }

    fun saveUsuario(usuario: Usuario): MutableLiveData<Usuario> {
        val recursos = MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            recursos.value = repository.saveUsuario(usuario)
        }
        return recursos
    }

    fun getDataUsuarioPorNickPass(nick:String, pass:String): MutableLiveData<Usuario> {
        val usuario = MutableLiveData<Usuario>()
        GlobalScope.launch(Dispatchers.Main) {
            usuario.value = repository.getDataUsuarioPorNickPass(nick, pass)
        }
        return usuario
    }

}