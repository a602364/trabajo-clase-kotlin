package net.azarquiel.bddv3.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.azarquiel.bddv3.model.Amigo
import net.azarquiel.bddv3.model.AmigoRepositories
import net.azarquiel.bddv3.model.Grupo
import net.azarquiel.bddv3.model.GrupoRepositories

class GrupoViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: GrupoRepositories = GrupoRepositories(application)

    fun getAllGrupos(): LiveData<List<Grupo>> {
        return repository.getAllGrupos()
    }

    fun insert(grupo: Grupo) {
        viewModelScope.launch {
            repository.insert(grupo)
            //launch(Dispatchers.Main)
        }
    }

    fun delete(grupo: Grupo){
        viewModelScope.launch {
            repository.delete(grupo)
            //launch(Dispatchers.Main)
        }
    }
}

class AmigoViewModel(application: Application) : AndroidViewModel(application) {

    private var repositoryAmigos: AmigoRepositories = AmigoRepositories(application)

    fun getAllAmigos(): LiveData<List<Amigo>> {
        return repositoryAmigos.getAllAmigos()
    }

    fun insert(amigo: Amigo) {
        viewModelScope.launch {
            repositoryAmigos.insert(amigo)
            //launch(Dispatchers.Main)
        }
    }

    fun delete(amigo: Amigo){
        viewModelScope.launch {
            repositoryAmigos.delete(amigo)
            //launch(Dispatchers.Main)
        }
    }
}