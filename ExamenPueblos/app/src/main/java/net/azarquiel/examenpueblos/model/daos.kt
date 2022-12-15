package net.azarquiel.examenpueblos.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update

@Dao
interface ComunidadDao {
    @Query("SELECT * from comunidad ORDER BY nombre ASC")
    fun getAllComunidades(): LiveData<List<Comunidad>>

}

@Dao
interface ProvinciaDao {
    @Query("SELECT p.id as id, p.nombre as nombre, p.comunidad as comunidad, (SELECT nombre FROM comunidad WHERE id = comunidad=:comunidadid) as nombreComunidad FROM provincia p WHERE p.comunidad = comunidad=:comunidadid ORDER BY id ASC")
    fun getAllProvincias(comunidadid: Int): LiveData<List<ProvinciaView>>

}

@Dao
interface PuebloDao {
    @Query("SELECT p.id as id, p.nombre as nombre, p.imagen as imagen, p.provincia as provincia, p.link as link, p.fav as fav, (SELECT nombre FROM provincia WHERE id = provincia=:provinciaid) as nombreProvincia FROM pueblo p WHERE p.provincia = provincia=:provinciaid ORDER BY id ASC")
    fun getAllPueblos(provinciaid: Int): LiveData<List<PuebloView>>

    @Query("SELECT p.id as id, p.nombre as nombre, p.imagen as imagen, p.provincia as provincia, p.link as link, p.fav as fav, (SELECT nombre FROM provincia WHERE id = p.provincia) as nombreProvincia\n" +
            "FROM pueblo p\n" +
            "JOIN provincia pv ON p.provincia = pv.id\n" +
            "JOIN comunidad c ON pv.comunidad = c.id\n" +
            "WHERE c.id = :comunidadid\n" +
            "ORDER BY nombre ASC")
    fun getPueblosByComunidad(comunidadid: Int): LiveData<List<PuebloView>>

    @Query("SELECT p.id as id, p.nombre as nombre, p.imagen as imagen, p.provincia as provincia, p.link as link, p.fav as fav, (SELECT nombre FROM provincia WHERE id = p.provincia) as nombreProvincia\n" +
            "FROM pueblo p\n" +
            "JOIN provincia pv ON p.provincia = pv.id\n" +
            "JOIN comunidad c ON pv.comunidad = c.id\n" +
            "WHERE c.id = :comunidadid\n" +
            "AND p.fav = 1\n" +
            "ORDER BY nombre ASC\n")
    fun getPueblosFav(comunidadid: Int): LiveData<List<PuebloView>>

    @Query("UPDATE pueblo SET fav = (SELECT CASE WHEN fav = 1 THEN 0 ELSE 1 END AS toggledFav FROM pueblo WHERE id =:id) WHERE  id =:id")
    fun updateFavPueblo(id: Int)
}
