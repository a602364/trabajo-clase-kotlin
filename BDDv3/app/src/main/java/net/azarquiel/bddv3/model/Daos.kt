package net.azarquiel.bddv3.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import net.azarquiel.bddv3.model.Amigo
import net.azarquiel.bddv3.model.Grupo


@Dao
interface GrupoDao {
    @Query("SELECT * FROM grupo ORDER BY nombre ASC")
    fun getAllGrupos(): LiveData<List<Grupo>>

    @Insert
    suspend fun insert(grupo: Grupo) //suspend para que no pete y no lo haga en la principal

    @Delete
    suspend fun delete(grupo: Grupo)
}

@Dao
interface AmigoDao {
    @Query("SELECT * FROM amigo ORDER BY grupo ASC")
    fun getAllAmigos(): LiveData<List<Amigo>>

    @Insert
    suspend fun insert(amigo: Amigo) //suspend para que no pete y no lo haga en la principal

    @Delete
    suspend fun delete(amigo: Amigo)
}