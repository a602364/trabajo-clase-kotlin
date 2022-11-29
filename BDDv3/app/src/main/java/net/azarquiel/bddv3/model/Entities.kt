package net.azarquiel.bddv3.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "amigo",
    foreignKeys = [ForeignKey(
        entity = Grupo::class,
        parentColumns = arrayOf("idGrupo"),
        childColumns = arrayOf("grupo")
    )]
)
data class Amigo(
    @PrimaryKey(autoGenerate = true)
    var idNombre: Int = 0,
    var nombre: String = "",
    var grupo: Int = 0,
    var color: Int = 0
) :Serializable

@Entity(tableName = "grupo")
data class Grupo(
    @PrimaryKey(autoGenerate = true)
    var idGrupo: Int = 0,
    var nombre: String = "",
    var email: String = "",
    var color: Int = 0
) : Serializable



data class Grupos(var grupos: List<Grupo>) : Serializable